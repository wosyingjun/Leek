package com.yingjun.stock.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.espertech.esper.client.EPServiceProvider;
import com.yingjun.stock.dto.StockRealTimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 过滤掉无效的行情以及集合竞价期间的行情数据
 */
public class StockFilterBolt extends BaseBasicBolt {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private OutputCollector collector;
    private EPServiceProvider epService;


    /**
     * 完成自己的逻辑
     */
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        StockRealTimeEvent event = (StockRealTimeEvent) tuple.getValue(0);
        if (event.getNewPrice() == 0) {
            log.info("过滤掉无效行情："+event);
            return;
        }
        if (event.getBuyPrice1() > 0 && event.getSellPrice1() > 0
                && (event.getBuyPrice2() + event.getSellPrice2()) == 0
                && (event.getBuyPrice5() + event.getSellPrice5()) == 0) {
            log.info("过滤掉无效行情："+event);
            return;
        }
        basicOutputCollector.emit(new Values(event));
    }

    /**
     * 生成tuple的Filelds ID
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("filter-stock"));
    }

}
