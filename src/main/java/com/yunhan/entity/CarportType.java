package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//车位类型实体类
@TableName("carportt")
public class CarportType extends DataEntity<CarportType> {

    @TableField("carportTname")
    private String carportTname;//车位类型名

    public String getCarportTname() {
        return carportTname;
    }

    public void setCarportTname(String carportTname) {
        this.carportTname = carportTname;
    }
}
