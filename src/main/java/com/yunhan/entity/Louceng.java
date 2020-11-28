package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//楼层实体类
@TableName("louceng")
public class Louceng extends DataEntity<Louceng> {

    @TableField(value = "loucengNO")
    private String loucengNO;//楼层编号

    @TableField(value = "buildingID")
    private String buildingID;//楼号编号        外键列引用于building楼号信息表的主键ID

    @TableField(exist = false)
    private GmdLou gmdLou;//楼号对象

    public String getLoucengNO() {
        return loucengNO;
    }

    public void setLoucengNO(String loucengNO) {
        this.loucengNO = loucengNO;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public GmdLou getGmdLou() {
        return gmdLou;
    }

    public void setGmdLou(GmdLou gmdLou) {
        this.gmdLou = gmdLou;
    }
}
