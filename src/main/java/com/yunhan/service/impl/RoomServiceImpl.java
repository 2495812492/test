package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Room;
import com.yunhan.mapper.roomMapper.RoomMapper;
import com.yunhan.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roomService")
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Override
    public List<Room> getRoomListPage(Integer page, Integer limit, Room room) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("first",(page - 1) * limit);
        map.put("pageSize",limit);
        if(!StringUtils.isEmpty(room.getClient()) && !StringUtils.isEmpty(room.getClient().getClientname()) ){//import org.springframework.util.StringUtils;
            //如果请求参数中的客户号不为空，就添加一个按客户名查询的条件
            map.put("clientname",room.getClient().getClientname());
        }
        return baseMapper.getRoomListPage(map);
    }

    @Override
    public Long getCountRoomListPage(Room room) {
        return baseMapper.getCountRoomListPage(room);
    }

    @Override
    public int getRoomCountByMap(Map<String, Object> map) {
        return baseMapper.getRoomCountByMap(map);
    }

    @Override
    public Room getRoomListByRoomId(String roomId) {
        return baseMapper.getRoomListByRoomId(roomId);
    }

    @Override
    public int upRoom(Room room) {
        return baseMapper.upRoom(room);
    }

    @Override
    public int delRoom(Room room) {
        return baseMapper.deleteById(room);
    }


}
