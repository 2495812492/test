package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

@TableName("payItemst")
public class PayItemsT extends DataEntity<PayItemsT> {
    @TableField(value = "patitemstName")
     private String patitemstName;

    public String getPatitemstName() {
        return patitemstName;
    }

    public void setPatitemstName(String patitemstName) {
        this.patitemstName = patitemstName;
    }
}
