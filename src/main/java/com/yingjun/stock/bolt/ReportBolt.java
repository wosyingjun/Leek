package com.yingjun.stock.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.yingjun.stock.dto.ResultStock;
import com.yingjun.stock.mysql.ConnectionPool;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Map;


/**
 * 数据回报
 */
public class ReportBolt extends BaseBasicBolt {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private QueryRunner query;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        log.info("----------ReportBolt 初始化...");
        query = new QueryRunner(ConnectionPool.getInstance().getDataSource());
    }

    /**
     * 完成自己的逻辑
     */
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        ResultStock resultStock = (ResultStock) tuple.getValue(0);

        String sql = "insert into result(stock_code,stock_price,total_value,stategy_id) values(?,?,?,?)";
        Object params[] = {resultStock.getStockCode(), resultStock.getNewPrice(),
                resultStock.getTotalValue(), resultStock.getStrategyId()};

        try {
            query.update(sql,params);
        } catch (SQLException e) {
           log.error("SQLException:", e);
        }

    }

    /**
     * 没有后续的 bolt，所以这个方法可以不实现
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
