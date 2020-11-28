package com.yunhan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunhan.common.base.DataEntity;

import java.util.Date;

//客户信息实体类
@TableName("clientmessage")
public class ClientMessage extends DataEntity<ClientMessage> {

    @TableField("clientname")
    private String clientname;//客户名

    @TableField("clientType")
    private String clientType;//客户类型

    @TableField("phone")
    private String phone;//手机号码

    @TableField("certificateType")
    private String certificateType;//证件类型

    @TableField("certificateNo")
    private String certificateNo;//证件号码

    @TableField("gender")
    private String gender;//性别

    @TableField("birthday")
    private Date birthday;//出生日期

    @TableField("nation")
    private String nation;//民族

    @TableField("educationID")
    private String educationID;//学历编号       外键列引用于educations学历表的主键ID

    @TableField(exist = false)
    private Educations educations;//学历对象

    @TableField("natives")
    private String natives;//籍贯

    @TableField("bankCardNum")
    private String bankCardNum;//银行卡号

    @TableField("clientStatus")
    private int clientStatus;//客户状态

    @TableField("carportID")
    private String carportID;//车位编号     外键列引用于carport车位表的主键ID

    @TableField(exist = false)
    private CarPort carPort;//车位对象


    @TableField(value = "carPortID")
    private String carPortId;//车位编号

    public String getCarPortId() {
        return carPortId;
    }

    public void setCarPortId(String carPortId) {
        this.carPortId = carPortId;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducationID() {
        return educationID;
    }

    public void setEducationID(String educationID) {
        this.educationID = educationID;
    }

    public Educations getEducations() {
        return educations;
    }

    public void setEducations(Educations educations) {
        this.educations = educations;
    }

    public String getNatives() {
        return natives;
    }

    public void setNatives(String natives) {
        this.natives = natives;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public int getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(int clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getCarportID() {
        return carportID;
    }

    public void setCarportID(String carportID) {
        this.carportID = carportID;
    }

    public CarPort getCarPort() {
        return carPort;
    }

    public void setCarPort(CarPort carPort) {
        this.carPort = carPort;
    }

}
