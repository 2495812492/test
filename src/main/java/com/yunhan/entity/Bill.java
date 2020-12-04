package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("bill")
public class Bill {
    @TableField(value = "billid",strategy = FieldStrategy.IGNORED)
    private int billid;
    @TableField("roomID")
    private String roomID;
    @TableField("endtime")
    private String endtime;
    @TableField("payproject")
    private  String payproject;
    @TableField("practical")
    private int  practical;
    @TableField("price")
    private int price;
    @TableField("assessment")
    private int assessment;
    @TableField("remark")
    private String remark;
    @TableField("homeNO")
    private String homeNO;
    @TableField("meterReadTime")
    private String meterReadTime;
    @TableField("patitemsName")
    private String patitemsName;
    @TableField("utility")
    private int utility;
    @TableField("payitemsmoney")
    private int payitemsmoney;

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPayproject() {
        return payproject;
    }

    public void setPayproject(String payproject) {
        this.payproject = payproject;
    }

    public int getPractical() {
        return practical;
    }

    public void setPractical(int practical) {
        this.practical = practical;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHomeNO() {
        return homeNO;
    }

    public void setHomeNO(String homeNO) {
        this.homeNO = homeNO;
    }

    public String getMeterReadTime() {
        return meterReadTime;
    }

    public void setMeterReadTime(String meterReadTime) {
        this.meterReadTime = meterReadTime;
    }

    public String getPatitemsName() {
        return patitemsName;
    }

    public void setPatitemsName(String patitemsName) {
        this.patitemsName = patitemsName;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public int getPayitemsmoney() {
        return payitemsmoney;
    }

    public void setPayitemsmoney(int payitemsmoney) {
        this.payitemsmoney = payitemsmoney;
    }
}
