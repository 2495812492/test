package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.ClientMessage;

import java.util.List;

public interface ClientMessageService extends IService<ClientMessage> {

    //通过map集合中的参数查询客户列表信息
    List<ClientMessage> getClientListPage(Integer page,Integer limit,ClientMessage clientMessage);

    //分页查询客户总记录数
    Long getCountClientListPage(ClientMessage clientMessage);

    //通过客户ID查询查询客户详情信息
    ClientMessage getClientById(String id);

}
