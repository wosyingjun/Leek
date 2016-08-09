package com.yingjun.stock.dto;

import java.io.Serializable;

/**
*
 * 行情 Bean
 *
 * @author yingjun
*
*/
public class StockRealTimeEvent implements Serializable{

    private String stockCode;
    private double newPrice;
    private double buyPrice1;
    private long buyCount1;
    private double buyPrice2;
    private long buyCount2;
    private double buyPrice3;
    private long buyCount3;
    private double buyPrice4;
    private long buyCount4;
    private double buyPrice5;
    private long buyCount5;
    private double sellPrice1;
    private long sellCount1;
    private double sellPrice2;
    private long sellCount2;
    private double sellPrice3;
    private long sellCount3;
    private double sellPrice4;
    private long sellCount4;
    private double sellPrice5;
    private long sellCount5;
    private int current; //当前成交手数


    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }


    public double getBuyPrice1() {
        return buyPrice1;
    }

    public void setBuyPrice1(double buyPrice1) {
        this.buyPrice1 = buyPrice1;
    }

    public long getBuyCount1() {
        return buyCount1;
    }

    public void setBuyCount1(long buyCount1) {
        this.buyCount1 = buyCount1;
    }

    public double getBuyPrice2() {
        return buyPrice2;
    }

    public void setBuyPrice2(double buyPrice2) {
        this.buyPrice2 = buyPrice2;
    }

    public long getBuyCount2() {
        return buyCount2;
    }

    public void setBuyCount2(long buyCount2) {
        this.buyCount2 = buyCount2;
    }

    public double getBuyPrice3() {
        return buyPrice3;
    }

    public void setBuyPrice3(double buyPrice3) {
        this.buyPrice3 = buyPrice3;
    }

    public long getBuyCount3() {
        return buyCount3;
    }

    public void setBuyCount3(long buyCount3) {
        this.buyCount3 = buyCount3;
    }

    public double getBuyPrice4() {
        return buyPrice4;
    }

    public void setBuyPrice4(double buyPrice4) {
        this.buyPrice4 = buyPrice4;
    }

    public long getBuyCount4() {
        return buyCount4;
    }

    public void setBuyCount4(long buyCount4) {
        this.buyCount4 = buyCount4;
    }

    public double getBuyPrice5() {
        return buyPrice5;
    }

    public void setBuyPrice5(double buyPrice5) {
        this.buyPrice5 = buyPrice5;
    }

    public long getBuyCount5() {
        return buyCount5;
    }

    public void setBuyCount5(long buyCount5) {
        this.buyCount5 = buyCount5;
    }


    public double getSellPrice1() {
        return sellPrice1;
    }

    public void setSellPrice1(double sellPrice1) {
        this.sellPrice1 = sellPrice1;
    }

    public long getSellCount1() {
        return sellCount1;
    }

    public void setSellCount1(long sellCount1) {
        this.sellCount1 = sellCount1;
    }

    public double getSellPrice2() {
        return sellPrice2;
    }

    public void setSellPrice2(double sellPrice2) {
        this.sellPrice2 = sellPrice2;
    }

    public long getSellCount2() {
        return sellCount2;
    }

    public void setSellCount2(long sellCount2) {
        this.sellCount2 = sellCount2;
    }

    public double getSellPrice3() {
        return sellPrice3;
    }

    public void setSellPrice3(double sellPrice3) {
        this.sellPrice3 = sellPrice3;
    }

    public long getSellCount3() {
        return sellCount3;
    }

    public void setSellCount3(long sellCount3) {
        this.sellCount3 = sellCount3;
    }

    public double getSellPrice4() {
        return sellPrice4;
    }

    public void setSellPrice4(double sellPrice4) {
        this.sellPrice4 = sellPrice4;
    }

    public long getSellCount4() {
        return sellCount4;
    }

    public void setSellCount4(long sellCount4) {
        this.sellCount4 = sellCount4;
    }

    public double getSellPrice5() {
        return sellPrice5;
    }

    public void setSellPrice5(double sellPrice5) {
        this.sellPrice5 = sellPrice5;
    }

    public long getSellCount5() {
        return sellCount5;
    }

    public void setSellCount5(long sellCount5) {
        this.sellCount5 = sellCount5;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "StockRealTimeEvent{" +
                "stockCode='" + stockCode + '\'' +
                ", newPrice=" + newPrice +
                ", buyPrice1=" + buyPrice1 +
                ", buyCount1=" + buyCount1 +
                ", buyPrice2=" + buyPrice2 +
                ", buyCount2=" + buyCount2 +
                ", buyPrice3=" + buyPrice3 +
                ", buyCount3=" + buyCount3 +
                ", buyPrice4=" + buyPrice4 +
                ", buyCount4=" + buyCount4 +
                ", buyPrice5=" + buyPrice5 +
                ", buyCount5=" + buyCount5 +
                ", sellPrice1=" + sellPrice1 +
                ", sellCount1=" + sellCount1 +
                ", sellPrice2=" + sellPrice2 +
                ", sellCount2=" + sellCount2 +
                ", sellPrice3=" + sellPrice3 +
                ", sellCount3=" + sellCount3 +
                ", sellPrice4=" + sellPrice4 +
                ", sellCount4=" + sellCount4 +
                ", sellPrice5=" + sellPrice5 +
                ", sellCount5=" + sellCount5 +
                ", current=" + current +
                '}';
    }
}