package com.yunhan.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yunhan.common.util.Encodes;
import com.yunhan.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Date;

/**
 * 公共字段自动填充
 * mybatis-plus开启了自动填充功能后，在insert 或update时就会自动填充实体中自动填充字段
 * createDate  创建时间
 * createId    创建用户ID
 * updateDate 修改时间
 * updateId   修改用户ID
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //判断实体是否有自动填充字段属性，如果有的话在做新增时才进行自动填充字段赋值操作
        if (hasSetter(metaObject,"param1","createDate")) {
            setFieldValByName("createDate", new Date(), metaObject);
            setFieldValByName("createId", getLoginUser().getId(), metaObject);
            setFieldValByName("updateDate", new Date(), metaObject);
            setFieldValByName("updateId", getLoginUser().getId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        //判断实体是否有自动填充字段属性，如果有的话在做修改时才进行自动填充字段赋值操作
        if (hasSetter(metaObject, "param1", "updateDate")) {
            setFieldValByName("updateDate", new Date(), metaObject);
            setFieldValByName("updateId", getLoginUser().getId(), metaObject);
        }
    }

    //使用java反射机制得到当前进行更新或修改的实体对象，然后判断当前实体对象是否有自动填充的字段
    public boolean hasSetter(MetaObject metaObject,String paramName, String property) {
        try {
            Object param1 = metaObject.getValue(paramName);
            if(param1 != null){
                Class<?> aClass = param1.getClass();
                PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(aClass, property);
                if(pd != null && pd.getWriteMethod() != null){
                    return true;
                }
            }
            return false;
        }catch (Exception e) {
            return false;
        }
    }

    //得到当前登录用户对象
    public User getLoginUser() {
        return Encodes.getLoginUser();
        //return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
