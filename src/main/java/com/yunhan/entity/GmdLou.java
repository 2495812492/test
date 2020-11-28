package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//GmdLou实体类与building表建立映射关系
@TableName("building")
public class GmdLou extends DataEntity<GmdLou> {

    //buildinaName属性与building表中的buildinaName建立映射关系
    @TableField(value = "buildingName")
    private String buildingName;

    @TableField(value = "loucengCount")
    private Integer loucengCount;//总楼层

    @TableField(value = "itemsid")
    private Integer itemsid;//方案类型ID

    @TableField(value = "buildingStatus")
    private Integer buildingStatus;//状态

    //方案类型对象
    @TableField(exist = false)
    private Items items;//多对一的关联关系

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getLoucengCount() {
        return loucengCount;
    }

    public void setLoucengCount(Integer loucengCount) {
        this.loucengCount = loucengCount;
    }

    public Integer getItemsid() {
        return itemsid;
    }

    public void setItemsid(Integer itemsid) {
        this.itemsid = itemsid;
    }

    public Integer getBuildingStatus() {
        return buildingStatus;
    }

    public void setBuildingStatus(Integer buildingStatus) {
        this.buildingStatus = buildingStatus;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
