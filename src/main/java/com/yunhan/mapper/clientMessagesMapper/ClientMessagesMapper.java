package com.yunhan.mapper.clientMessagesMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.ClientMessage;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ClientMessagesMapper extends BaseMapper<ClientMessage> {

    //通过map集合中的参数查询客户列表信息
    List<ClientMessage> getClientListPage(Map<String,Object> map);

    //分页查询客户总记录数
    Long getCountClientListPage(ClientMessage clientMessage);

    //通过客户ID查询查询客户详情信息
    ClientMessage getClientById(@Param("id") String id);
}
