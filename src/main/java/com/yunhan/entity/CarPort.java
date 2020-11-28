package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunhan.common.base.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("carport")
public class CarPort extends DataEntity<CarPort> {

    @TableField("carportNo")
    private String carportNo;//车位编号

    @TableField("carportTid")
    private String carportTid;//车位类型ID(1表示租赁车位，2表示购买车位)     外键列引用于carportt车位类型表的主键ID

    @TableField(exist = false)
    private CarportType carportType;//车位状态对象

    @TableField(exist = false)
    private ClientMessage client; //车位是属于某个客户对象的。

    @TableField(exist = false)
    private Room room; //车位是属于某个客户对象的。

    @TableField("cartportStatus")
    private int cartportStatus;//车位状态

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("cbeginTime")
    private Date cbeginTime;//计费时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("cendTime")
    private Date cendTime;//结束时间

    @TableField(exist = false)
    private String clientMessageId;//客户ID

    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }

    public Room getRoom() {
        return room;
    }



    public void setRoom(Room room) {
        this.room = room;
    }


    public String getCarportNo() {
        return carportNo;
    }

    public ClientMessage getClient() {
        return client;
    }

    public void setClient(ClientMessage client) {
        this.client = client;
    }

    public void setCarportNo(String carportNo) {
        this.carportNo = carportNo;
    }

    public String getCarportTid() {
        return carportTid;
    }

    public void setCarportTid(String carportTid) {
        this.carportTid = carportTid;
    }

    public CarportType getCarportType() {
        return carportType;
    }

    public void setCarportType(CarportType carportType) {
        this.carportType = carportType;
    }

    public int getCartportStatus() {
        return cartportStatus;
    }

    public void setCartportStatus(int cartportStatus) {
        this.cartportStatus = cartportStatus;
    }

    public Date getCbeginTime() {
        return cbeginTime;
    }

    public void setCbeginTime(Date cbeginTime) {
        this.cbeginTime = cbeginTime;
    }

    public Date getCendTime() {
        return cendTime;
    }

    public void setCendTime(Date cendTime) {
        this.cendTime = cendTime;
    }
}
