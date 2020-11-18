package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

@TableName("items")
public class Items extends DataEntity<Items> {
    @TableField(value = "itemsname")
    private String iteamsname;

    public String getIteamsname() {
        return iteamsname;
    }

    public void setIteamsname(String iteamsname) {
        this.iteamsname = iteamsname;
    }
}
