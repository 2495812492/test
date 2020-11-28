package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.ClientMessage;
import com.yunhan.mapper.clientMessagesMapper.ClientMessagesMapper;
import com.yunhan.service.ClientMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("clientMessagesService")
@Transactional(rollbackFor = Exception.class)
public class ClientMessageServiceImpl extends ServiceImpl<ClientMessagesMapper, ClientMessage> implements ClientMessageService {

    @Override
    public List<ClientMessage> getClientListPage(Integer page, Integer limit, ClientMessage clientMessage) {
        Integer first = (page - 1) * limit;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("first",first);
        map.put("pageSize",limit);
        if(!StringUtils.isEmpty(clientMessage.getClientname())){
            map.put("clientname",clientMessage.getClientname());
        }
        return baseMapper.getClientListPage(map);
    }

    @Override
    public Long getCountClientListPage(ClientMessage clientMessage) {
        return baseMapper.getCountClientListPage(clientMessage);
    }

    @Override
    public ClientMessage getClientById(String id) {
        return baseMapper.getClientById(id);
    }
}
