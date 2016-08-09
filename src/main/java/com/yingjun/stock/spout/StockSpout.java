package com.yingjun.stock.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.alibaba.fastjson.JSONObject;
import com.yingjun.stock.dto.StockRealTimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 从本地日志中读取行情数据。
 *
 * 已经改为用KafKaSpout获取数据
 *
 */
@Deprecated()
public class StockSpout extends BaseRichSpout {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private SpoutOutputCollector collector;
    private BufferedReader bufferedReader;

    /**
     * open是当task起来后执行的初始化动作
     *
     * @param conf
     * @param context
     * @param collector
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

        //从context对象获取spout大小
        int spoutsSize=context.getComponentTasks(context.getThisComponentId()).size();
        //从这个spout得到任务id
        int myIdx = context.getThisTaskIndex();

        this.collector = collector;
        Properties p = new Properties();
        try {
            InputStream inputStream = StockSpout.class.getClassLoader().getResourceAsStream("config.properties");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            p.load(bufferedReader);
            bufferedReader = new BufferedReader(new FileReader(new File(p.getProperty("stockLogFile"))));
        } catch (IOException e) {
            log.error("IOException:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * nextTuple 是spout实现核心，
     * 用collector 将消息emit出去。
     */
    @Override
    public void nextTuple() {
        String json = null;
        try {
            if ((json = bufferedReader.readLine()) != null) {
                //日志数据转为java对象
                log.info("emit:" + json);
                try {
                    StockRealTimeEvent stockRealTimeEvent = JSONObject.parseObject(json, StockRealTimeEvent.class);
                    Values values = new Values(stockRealTimeEvent);
                    //定义ID，保证消息的可靠性
                    collector.emit(values, stockRealTimeEvent);
                } catch (Exception e) {
                    log.info("Exception:", e);
                }
            } else {
                //当日志中无数据可读时，休息1秒。
                Utils.sleep(1000);
            }
        } catch (Exception e) {
            log.error("IOException:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 定义spout发送数据，每个字段的含义
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // 方法声明该bolt发射包含一个 StockSpout 字段的 tuple
        declarer.declare(new Fields("line"));
    }

    @Override
    public void ack(Object msgId) {
        //确认消息被完整处理
        log.info("-----------ack:" + msgId);
    }

    @Override
    public void fail(Object msgId) {
        //消息处理失败，纪录进MQ
        log.info("-----------fail:" + msgId);
    }
}
