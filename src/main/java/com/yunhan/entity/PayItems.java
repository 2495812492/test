package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

import java.math.BigDecimal;

//PayItems实体类与数据库中payitems表建立映射关系
@TableName("payitems")
public class PayItems extends DataEntity<PayItems> {

    @TableField(value = "patitemsName")
    private String patitemsName;  //收费项目名称

    @TableField(value = "metertID")
    private String metertID;  //仪表类型ID

    @TableField(exist = false)
    private MeterType meterType;  //仪表类型对象
    @TableField(value = "payitemstId")
    private String payitemstId;  //收费类型表ID

    @TableField(exist = false)
    private PayItemsT payItemsType;  //收费类型对象
    @TableField(value = "payitemsmoney")
    private BigDecimal payitemsmoney;  //执行金额
    @TableField(value = "itemsid")
    private String itemsid;

    @TableField(exist = false)
    private Items items;
    //省略了getter/setter方法

    public String getPatitemsName() {
        return patitemsName;
    }

    public void setPatitemsName(String patitemsName) {
        this.patitemsName = patitemsName;
    }

    public String getMetertID() {
        return metertID;
    }

    public void setMetertID(String metertID) {
        this.metertID = metertID;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public String getPayitemstId() {
        return payitemstId;
    }

    public void setPayitemstId(String payitemstId) {
        this.payitemstId = payitemstId;
    }

    public PayItemsT getPayItemsType() {
        return payItemsType;
    }

    public void setPayItemsType(PayItemsT payItemsType) {
        this.payItemsType = payItemsType;
    }

    public BigDecimal getPayitemsmoney() {
        return payitemsmoney;
    }

    public void setPayitemsmoney(BigDecimal payitemsmoney) {
        this.payitemsmoney = payitemsmoney;
    }

    public String getItemsid() {
        return itemsid;
    }

    public void setItemsid(String itemsid) {
        this.itemsid = itemsid;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
