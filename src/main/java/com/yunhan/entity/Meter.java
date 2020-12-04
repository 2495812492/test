package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yunhan.common.base.DataEntity;

public class Meter extends DataEntity<Meter> {
    @TableField("meterID")
    private Integer meterID;
    @TableField("precinct")
    private String precinct;
    @TableField("homeNO")
    private String homeNO;
    @TableField("roomNo")
    private String roomNo;
    @TableField("meterNO")
    private String meterNO;
    @TableField("clientName")
    private String clientName;
    @TableField("payitemsId")
    private int payitemsId;
    @TableField("meterReadTime")
    private String meterReadTime;
    @TableField("meterReadEndTime")
    private String meterReadEndTime;
    @TableField("lastIndex")
    private int lastIndex;
    @TableField("atIndex")
    private int atIndex;
    @TableField("additional")
    private int additional;
    @TableField("utility")
    private int utility;
    @TableField("meterReadType")
    private String meterReadType;
    @TableField("meterReadStatus")
    private String meterReadStatus;
    @TableField("auditStatus")
    private int auditStatus;
    @TableField("meterReadDate")
    private String meterReadDate;
    @TableField("meterReader")
    private String meterReader;
    @TableField("remark")
    private String remark;
    @TableField("payitemstId")
    private int payitemstId;

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getMeterID() {
        return meterID;
    }

    public void setMeterID(Integer meterID) {
        this.meterID = meterID;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getHomeNO() {
        return homeNO;
    }

    public void setHomeNO(String homeNO) {
        this.homeNO = homeNO;
    }

    public String getMeterNO() {
        return meterNO;
    }

    public void setMeterNO(String meterNO) {
        this.meterNO = meterNO;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getPayitemsId() {
        return payitemsId;
    }

    public void setPayitemsId(int payitemsId) {
        this.payitemsId = payitemsId;
    }

    public String getMeterReadTime() {
        return meterReadTime;
    }

    public void setMeterReadTime(String meterReadTime) {
        this.meterReadTime = meterReadTime;
    }

    public String getMeterReadEndTime() {
        return meterReadEndTime;
    }

    public void setMeterReadEndTime(String meterReadEndTime) {
        this.meterReadEndTime = meterReadEndTime;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getAtIndex() {
        return atIndex;
    }

    public void setAtIndex(int atIndex) {
        this.atIndex = atIndex;
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public String getMeterReadType() {
        return meterReadType;
    }

    public void setMeterReadType(String meterReadType) {
        this.meterReadType = meterReadType;
    }

    public String getMeterReadStatus() {
        return meterReadStatus;
    }

    public void setMeterReadStatus(String meterReadStatus) {
        this.meterReadStatus = meterReadStatus;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getMeterReadDate() {
        return meterReadDate;
    }

    public void setMeterReadDate(String meterReadDate) {
        this.meterReadDate = meterReadDate;
    }

    public String getMeterReader() {
        return meterReader;
    }

    public void setMeterReader(String meterReader) {
        this.meterReader = meterReader;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPayitemstId() {
        return payitemstId;
    }

    public void setPayitemstId(int payitemstId) {
        this.payitemstId = payitemstId;
    }
}
