package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

//PayItems实体类与数据库中payitems表建立映射关系
@TableName("payitems")
public class PayItems extends DataEntity<PayItems> {

    @TableField(value = "patitemsName")
    private String patitemsName;  //收费项目名称

    @TableField(value = "begintime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begintime;//使用时间

    @TableField(value = "metertID")
    private String metertID;  //仪表类型ID

    @TableField(exist = false)
    private Metert metert;  //仪表类型对象

    @TableField(value = "payitemstId")
    private String payitemstId;  //收费类型表ID

    @TableField(exist = false)
    private PayItemsT payitemst;  //收费类型对象

    @TableField(value = "payitemsmoney")
    private BigDecimal payitemsmoney;  //执行金额

    @TableField(value = "itemsid")
    private String itemsid;

    @TableField(exist = false)
    private Items items;
    //省略了getter/setter方法


    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

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

    public Metert getMetert() {
        return metert;
    }

    public void setMetert(Metert metert) {
        this.metert = metert;
    }

    public String getPayitemstId() {
        return payitemstId;
    }

    public void setPayitemstId(String payitemstId) {
        this.payitemstId = payitemstId;
    }

    public PayItemsT getPayitemst() {
        return payitemst;
    }

    public void setPayitemst(PayItemsT payitemst) {
        this.payitemst = payitemst;
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
