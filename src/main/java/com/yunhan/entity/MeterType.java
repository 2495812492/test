package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//MeterType实体类与meter表建立映射关系
@TableName("metert")
public class MeterType extends DataEntity<MeterType> {
    @TableField(value = "meterType",strategy= FieldStrategy.IGNORED)
    private Integer meterType;
    private String mname;

    public Integer getMeterType() {
        return meterType;
    }

    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
