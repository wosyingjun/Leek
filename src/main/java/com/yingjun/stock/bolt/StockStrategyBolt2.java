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
 * 股票策略2（大买盘）
 * 当股票的买5档总手数大于卖5档口手数的100倍时选出股票。
 *
 * @author yingjun
 */
public class StockStrategyBolt2 extends BaseBasicBolt {

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
        log.info("----------股票策略2（大买盘）初始化...");
        Configuration configuration = new Configuration();
        configuration.addEventType("StockRealTimeEvent", StockRealTimeEvent.class.getName());
        epService = EPServiceProviderManager.getProvider("strategy2",configuration);

        //提取出卖盘口远大于买盘口的行情数据
        EPStatement stmt = epService.getEPAdministrator()
                .createEPL("select * from StockRealTimeEvent where " +
                        "(buyCount5+buyCount4+buyCount3+buyCount2+buyCount1)" +
                        ">=(sellCount5+sellCount4+sellCount3+sellCount2+sellCount1)*100");

        stmt.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                if (newEvents != null) {
                    EventBean theEvent = newEvents[0];
                    StockRealTimeEvent stockRTEvent = (StockRealTimeEvent) theEvent.getUnderlying();
                    log.info("---------- 股票策略2（大买盘）选出股票：" + stockRTEvent.getStockCode() + " 最新价：" + stockRTEvent.getNewPrice());
                    outputCollector.emit(new Values(new ResultStock(stockRTEvent.getStockCode(),stockRTEvent.getNewPrice(),2)));
                }
            }
        });
    }

    /**
     * 完成自己的逻辑
     */
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        this.outputCollector=basicOutputCollector;
        StockRealTimeEvent stockRealTimeEvent = (StockRealTimeEvent) tuple.getValue(0);
        log.info("策略2（大买盘）===> Esper：" + stockRealTimeEvent);
        epService.getEPRuntime().sendEvent(stockRealTimeEvent);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("StockStrategy2"));
    }

    @Override
    public void cleanup() {
        if(!epService.isDestroyed()){
            epService.destroy();
        }
    }

}
