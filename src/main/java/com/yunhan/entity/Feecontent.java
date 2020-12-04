package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunhan.common.base.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@TableName("feecontent")
public class Feecontent extends DataEntity<CarPort> {
    @TableField(value = "feeID",strategy = FieldStrategy.IGNORED)
    private Integer feeID;
    @TableField("roomID")
    private String roomID;
    @TableField("roomNo")
    private String roomNo;
    @TableField("clientMessageId")
    private String clientMessageId;
    @TableField("clientname")
    private String clientname;
    @TableField("payproject")
    private String payproject;
    @TableField("balance")
    private BigDecimal balance;
    @TableField("assessment")
    private BigDecimal assessment;
    @TableField("paymentType")
    private String paymentType;
    @TableField("tollgatherer")
    private String tollgatherer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("collectDate")
    private Date collectDate;
    @TableField("fremark")
    private String fremark;

    public Integer getFeeID() {
        return feeID;
    }

    public void setFeeID(Integer feeID) {
        this.feeID = feeID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getPayproject() {
        return payproject;
    }

    public void setPayproject(String payproject) {
        this.payproject = payproject;
    }

    public BigDecimal getAssessment() {
        return assessment;
    }

    public void setAssessment(BigDecimal assessment) {
        this.assessment = assessment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTollgatherer() {
        return tollgatherer;
    }

    public void setTollgatherer(String tollgatherer) {
        this.tollgatherer = tollgatherer;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }
}
