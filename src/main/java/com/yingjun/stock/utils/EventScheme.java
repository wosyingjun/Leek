package com.yingjun.stock.utils;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.alibaba.fastjson.JSONObject;
import com.yingjun.stock.dto.StockRealTimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yingjun
 */
public class EventScheme implements Scheme {

    private static final Logger log = LoggerFactory.getLogger(EventScheme.class);

    @Override
    public List<Object> deserialize(byte[] bytes) {
        try {
            String msg = new String(bytes, "UTF-8");
            StockRealTimeEvent stockRealTimeEvent = JSONObject.parseObject(msg, StockRealTimeEvent.class);
            Values values = new Values(stockRealTimeEvent);
            return values;
        } catch (Exception e) {
            log.error("Exception:", e);
        }
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("msg");
    }
}
