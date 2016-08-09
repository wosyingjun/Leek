package com.yingjun.stock.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.yingjun.stock.bolt.*;
import com.yingjun.stock.utils.EventScheme;
import org.apache.thrift.TException;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import java.util.Arrays;

/**
 * @author yingjun
 */
public class StockStategyTopology {

    public static void main(String[] args) throws TException {

        // Configure Kafka
        String zks="192.168.xx.100:2181,192.168.xx.101:2181,192.168.xx.102:2181";
        String topic = "stock";
        // default zookeeper root configuration for storm
        String zkRoot = "/kafkaStorm";
        String spoutId = "kafkaSpout";
        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);
        spoutConfig.scheme = new SchemeAsMultiScheme(new EventScheme());
        //该Topology因故障停止处理，下次正常运行时是否从Spout对应数据源Kafka中的该订阅Topic的起始位置开始读取
        spoutConfig.forceFromStart = false;
        spoutConfig.zkServers = Arrays.asList(new String[] {"192.168.xx.100","192.168.xx.101","192.168.xx.102"});
        spoutConfig.zkPort = 2181;

        //创建topology的生成器
        TopologyBuilder builder = new TopologyBuilder();

        // Kafka里创建了一个2分区的Topic，这里并行度设置为2
        builder.setSpout("kafka-reader", new KafkaSpout(spoutConfig), 2)
                .setNumTasks(2);

        // 设置数据处理节点名称，实例，并行度。
        builder.setBolt("stock-filter", new StockFilterBolt(), 2)//设置2个并行度（executor）
                .setNumTasks(2)//设置关联task个数
                .shuffleGrouping("kafka-reader");

        builder.setBolt("stock-stategy-1", new StockStrategyBolt1(), 2)
                .setNumTasks(2)
                .shuffleGrouping("stock-filter");
        builder.setBolt("stock-stategy-2", new StockStrategyBolt2(), 2)
                .setNumTasks(2)
                .shuffleGrouping("stock-filter");
        builder.setBolt("stock-stategy-3", new StockStrategyBolt3(), 2)
                .setNumTasks(2)
                .shuffleGrouping("stock-filter");
        builder.setBolt("report", new ReportBolt(), 1)
                .setNumTasks(2)
                .shuffleGrouping("stock-stategy-1")
                .shuffleGrouping("stock-stategy-2")
                .shuffleGrouping("stock-stategy-3");

        Config config = new Config();
        //设置一个spout task上面最多可以多少个没有处理的tuple，以防止tuple队列爆掉
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 10000);
        config.setDebug(false);
        //分配几个进程来运行这个这个topology，建议大于物理机器数量。
        config.setNumWorkers(2);
        String name = StockStategyTopology.class.getSimpleName();

        if (args != null && args.length > 0) {
            // Nimbus host name passed from command line
            config.put(Config.NIMBUS_HOST, args[0]);
            StormSubmitter.submitTopologyWithProgressBar(name, config, builder.createTopology());
        } else {
            // 这里是本地模式下运行的启动代码。
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", config, builder.createTopology());
        }

    }
}
