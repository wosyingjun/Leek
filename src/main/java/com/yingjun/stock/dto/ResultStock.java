package com.yingjun.stock.dto;

import java.io.Serializable;

/**
 * @author yingjun
 */
public class ResultStock implements Serializable {

    private String stockCode;
    private double newPrice;
    private double totalValue;
    private int strategyId;

    public ResultStock() {

    }

    public ResultStock(String stockCode, double newPrice, int strategyId) {
        this.stockCode = stockCode;
        this.newPrice = newPrice;
        this.strategyId = strategyId;
    }

    public ResultStock(String stockCode, double newPrice, int strategyId, double totalValue) {
        this.stockCode = stockCode;
        this.strategyId = strategyId;
        this.totalValue = totalValue;
        this.newPrice = newPrice;
    }

    public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
