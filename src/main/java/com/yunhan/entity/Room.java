package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

@TableName("room")
public class Room extends DataEntity<Room> {
    @TableField("roomNo")
    private String roomNo;//房间编号

    @TableField("roomArea")
    private Long roomArea;//建筑面积

    @TableField("houseType")
    private String houseType;//户型

    @TableField("finish")
    private String finish;//装修类型

    @TableField("houseState")
    private int houseState;//房间状态       1为已入住  0为未入住

    @TableField("clientMessageId")
    private String clientMessageId;//客户编号

    @TableField("loucengID")
    private String loucengID;//楼层编号

    //多对一，多个房屋是属于某一个用户
    @TableField(exist = false)
    private ClientMessage client;//客户对象/业主对象

    //多对一，多个房屋属于一个楼层
    @TableField(exist = false)
    private Louceng louCeng;//楼层对象

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Long getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Long roomArea) {
        this.roomArea = roomArea;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public int getHouseState() {
        return houseState;
    }

    public void setHouseState(int houseState) {
        this.houseState = houseState;
    }

    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }

    public String getLoucengID() {
        return loucengID;
    }

    public void setLoucengID(String loucengID) {
        this.loucengID = loucengID;
    }

    public ClientMessage getClient() {
        return client;
    }

    public void setClient(ClientMessage client) {
        this.client = client;
    }

    public Louceng getLouCeng() {
        return louCeng;
    }

    public void setLouCeng(Louceng louCeng) {
        this.louCeng = louCeng;
    }
}
