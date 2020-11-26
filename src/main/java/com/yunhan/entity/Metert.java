package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//MeterType实体类与meter表建立映射关系
@TableName("metert")
public class Metert extends DataEntity<Metert> {
    @TableField(value = "metertType")
    private String metertType;

    public String getMetertType() {
        return metertType;
    }

    public void setMetertType(String metertType) {
        this.metertType = metertType;
    }
}
