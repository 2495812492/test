package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunhan.common.base.PageData;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.*;
import com.yunhan.service.ClientMessageService;
import com.yunhan.service.GmdLouService;
import com.yunhan.service.LouCengService;
import com.yunhan.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//房间档案的控制器，负责处理房间档案的相关信息
@Controller
public class RoomController {

    @RequestMapping("/groom")
    public String meter(){
        return "groom";     //负责跳转到groom.html房间档案列表页面
    }

    @Resource
    private RoomService roomService;

    @Resource
    private GmdLouService gmdLouService;

    @Resource
    private LouCengService louCengService;

    @Resource
    private ClientMessageService clientMessageService;

    /**
     * 分页查询房间档案列表信息
     * @param page  当前页码
     * @param limit 每页显示的数据行数
     */
    @RequestMapping("glistData")
    @ResponseBody
    public PageData<Room> listData(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,Room room){
        PageData<Room> pageData = new PageData<>();
        Long count = roomService.getCountRoomListPage(room);
        List<Room> roomList = roomService.getRoomListPage(page,limit,room);
        pageData.setCount(count);
        pageData.setData(roomList);
        return pageData;
    }

    //跳转添加addgroom房间添加页面
    @RequestMapping("/addroom")
    public String addroom(Model model){
        QueryWrapper<GmdLou> wrapper = new QueryWrapper<GmdLou>();
        wrapper.eq("buildingStatus",0);
        List<GmdLou> louList = gmdLouService.list(wrapper);//building楼号信息表
        model.addAttribute("louList",louList);
        return "addgroom";
    }

    //根据楼号id查询所对应的楼层列表
    @RequestMapping("/selBs")
    @ResponseBody
    public ResponseEntity selBs(String buildingId){
        QueryWrapper<Louceng> wrapper = new QueryWrapper<>();
        wrapper.eq("buildingID",buildingId);

        List<Louceng> loucengList = louCengService.list(wrapper);
        ResponseEntity entity = new ResponseEntity();
        return entity.setAny("data",loucengList);
    }

    //保存对房间信息的添加操作
    @RequestMapping("addRoom")
    @ResponseBody
    public ResponseEntity addRoom(Room room){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roomNo",room.getRoomNo());
        map.put("louCengId",room.getLouCeng().getId());
        map.put("buildingId",room.getLouCeng().getGmdLou().getId());
        int count = roomService.getRoomCountByMap(map);
        if(count > 0){
            return ResponseEntity.failure("该房间信息已存在，不能重复添加！");
        }else{
            room.setCreateDate(new Date());
            boolean flag = roomService.save(room);
            if(flag){
                return ResponseEntity.success("房间信息添加成功！");
            }else{
                return ResponseEntity.success("房间信息添加失败！");
            }
        }
    }

    //通过房间ID查询房间信息后，跳转到房间修改页面，展示当前房间详细信息
    @RequestMapping("/upSel")
    public String upSel(Room room,Model model){
        Room r = roomService.getRoomListByRoomId(room.getId());
        model.addAttribute("r",r);
        return "upgroom";
    }

    //修改
    @RequestMapping("/upRoom")
    @ResponseBody
    public ResponseEntity upRoom(Room room){
        //获取当前登录的用户对象
        User user = Encodes.getLoginUser();
        room.setUpdateId(user.getId());
        int result = roomService.upRoom(room);
        if(result > 0){
            return ResponseEntity.success("修改成功！");
        }else{
            return ResponseEntity.success("修改失败！");
        }
    }

    //添加时查询单元号楼层号和客户信息(添加户主)
    @RequestMapping("/addSel")
    public String addSel(Room room,Model model){
        Room r = roomService.getRoomListByRoomId(room.getId());
        List<ClientMessage> clientMessages = clientMessageService.list(null);
        model.addAttribute("r",r);
        model.addAttribute("clientMessages",clientMessages);
        return "addgbtnroom";
    }

    //执行添加并修改房间状态
    @RequestMapping("addBtnRoom")
    @ResponseBody
    public ResponseEntity addBtnRoom(Room room){
        //获取当前登录的用户对象
        User user = Encodes.getLoginUser();
        room.setUpdateId(user.getId());
        room.setHouseState(1);
        //调用service中的upRoom实现对room表信息的修改操作
        int result = roomService.upRoom(room);
        if(result > 0){
            return ResponseEntity.success("添加户主成功！");
        }else{
            return ResponseEntity.success("添加户主失败！");
        }
    }

    //删除
    @RequestMapping("/delRoom")
    @ResponseBody
    public ResponseEntity delRoom(Room room){
        int result = roomService.delRoom(room);
        if(result > 0){
            return ResponseEntity.success("删除成功！");
        }else{
            return ResponseEntity.success("删除失败！");
        }
    }

}
