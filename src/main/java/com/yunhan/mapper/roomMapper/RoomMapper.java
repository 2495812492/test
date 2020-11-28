package com.yunhan.mapper.roomMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Room;

import java.util.List;
import java.util.Map;

public interface RoomMapper extends BaseMapper<Room> {

    //通过map集合中的参数查询房间列表信息
    List<Room> getRoomListPage(Map<String,Object> map);

    //分页查询房间总记录数
    Long getCountRoomListPage(Room room);

    //添加房间时查询同一单元同一楼层是否存在
    int getRoomCountByMap(Map<String,Object> map);

    //通过房间ID关联查询room房间表、louceng楼层表、clientMessage客户信息表相关的房间信息
    Room getRoomListByRoomId(String roomId);

    //修改
    int upRoom(Room room);

    //删除
    int delRoom(Room room);

}
