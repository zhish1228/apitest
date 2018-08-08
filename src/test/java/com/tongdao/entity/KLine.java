//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tongdao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class KLine implements Serializable, Cloneable {
  private String id;
  private String market;
  private String name;
  private String code;
  private int period;
  private int date;
  private int time;
  private long barTime;
  private int startDate;
  private int startMin;
  private long open;
  private long high;
  private long low;
  private long close;
  private long closeChange;
  private long closeChangeRate;
  private long settlement;
  private long amount;
  private long amountChange;
  private long businessAmount;
  private long businessAmountChange;
  private long businessBalance;
  private long turnoverRatio;
  private long sumToEndBusinessAmount;
  private long sumToEndBusinessBalance;
  private long lastClose;
  private long periodTime;
  private Long updateTime;
  private Long createTime;
  private int tradePeriodNums;
  private boolean isLast;
  private int isLastBar;
  private boolean isSliced = false;

}
