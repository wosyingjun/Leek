package com.yingjun.stock.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.espertech.esper.client.*;
import com.yingjun.stock.dto.ResultStock;
import com.yingjun.stock.dto.StockRealTimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 股票策略3（放巨量）
 * 选出在10秒内成交量超过1000万的股票
 *
 * @author yingjun
 */
public class StockStrategyBolt3 extends BaseBasicBolt {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private BasicOutputCollector outputCollector;
    private EPServiceProvider epService;

    /**
     * 是当task起来后执行的初始化动作
     * 这里初始化Esper
     *
     * @param stormConf
     * @param context
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        log.info("----------股票策略3（放巨量）初始化...");
        Configuration configuration = new Configuration();
        configuration.addEventType("StockRealTimeEvent", StockRealTimeEvent.class.getName());
        epService = EPServiceProviderManager.getProvider("strategy3", configuration);

        //筛选出10秒内成交量大于1000万的股票
        EPStatement stmt = epService.getEPAdministrator()
                .createEPL("select stockCode,newPrice,sum(newPrice*current*100) as totalValue " +
                        "from StockRealTimeEvent.win:time(10 sec) group by stockCode " +
                        "having sum(newPrice*current*100)>10000000");

        stmt.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                if (newEvents != null) {
                    EventBean theEvent = newEvents[0];
                    double totalValue = (Double) theEvent.get("totalValue");
                    double newPrice = (Double) theEvent.get("newPrice");
                    String stockCode = (String) theEvent.get("stockCode");
                    log.info("---------- 股票策略3（放巨量）选出股票：" + stockCode + " 最新价：" + newPrice + " 成交额:" + totalValue);
                    outputCollector.emit(new Values(new ResultStock(stockCode,newPrice,3,totalValue)));
                }
            }
        });
    }

    /**
     * 完成自己的逻辑
     *
     * @param input
     */
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        this.outputCollector=basicOutputCollector;
        StockRealTimeEvent stockRealTimeEvent = (StockRealTimeEvent) tuple.getValue(0);
        log.info("策略3（放巨量）===> Esper：" + stockRealTimeEvent);
        epService.getEPRuntime().sendEvent(stockRealTimeEvent);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("StockStrategy3"));
    }

    @Override
    public void cleanup() {
        if (!epService.isDestroyed()) {
            epService.destroy();
        }
    }
}
