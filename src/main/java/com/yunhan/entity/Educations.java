package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

//学历实体类
@TableName("educations")
public class Educations extends DataEntity<Educations> {

    @TableField("education")
    private String education;//学历

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
