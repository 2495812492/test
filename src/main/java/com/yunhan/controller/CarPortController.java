package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yunhan.common.base.PageData;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.CarPort;
import com.yunhan.entity.CarportType;
import com.yunhan.entity.ClientMessage;
import com.yunhan.service.CarPortService;
import com.yunhan.service.CarPortTypeService;
import com.yunhan.service.ClientMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CarPortController {

    @Resource
    private CarPortService carPortService;

    //负责跳转到 templates/gcar.html 车位档案列表页面
    @RequestMapping("/gcar")
    public String gcar(){
        return "gcar";
    }

    /**
     * 分页查询房间档案列表信息
     * @param page  当前页码
     * @param limit 每页显示的数据行数
     */
    @RequestMapping("/gcarlistDate")
    @ResponseBody
    public PageData<CarPort> listData(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit, CarPort carPort) {

        PageData<CarPort> portPageData = new PageData<CarPort>();
        //获取分页查询时的总记录数
        Long count = carPortService.getCountPortListPage(carPort);
        //调用service的getRoomListPage方法，实现分页查询房间列表信息
        List<CarPort> carPortList= carPortService.getPortListPage(page, limit, carPort);
        portPageData.setData(carPortList);
        portPageData.setCount(count);
        return portPageData;
    }



    @Resource
    private CarPortTypeService carPortTypeService;

    //查询车位信息表
    @RequestMapping("/selCarportT")
    public String selCarportT(Model model){
        List<CarportType> gc = carPortTypeService.list(null);
        model.addAttribute("gc",gc);
        return "addgcar";
    }


    //车位信息添加操作
    @RequestMapping("/addCar")
    @ResponseBody
    public ResponseEntity addCar(CarPort carPort){
        QueryWrapper<CarPort> wrapper = new QueryWrapper<CarPort>();
        wrapper.eq("carportNo",carPort.getCarportNo());
        int count = carPortService.count(wrapper);
        if(count > 0){
            return ResponseEntity.success("车位已存在,不可重复添加！");
        }else{
            boolean flag = carPortService.save(carPort);
            if(flag){
                return  ResponseEntity.success("车位信息添加成功！");
            }else{
                return  ResponseEntity.success("车位信息添加失败！");
            }
        }
    }


    //禁用
    @RequestMapping("/delCar")
    @ResponseBody
    public ResponseEntity delCar(@RequestParam("id") String id){
        CarPort carPort = carPortService.getById(id);
        carPort.setCartportStatus(0);
        boolean flag = carPortService.saveOrUpdate(carPort);
        if(flag){
            return ResponseEntity.success("车位禁用成功！");
        }else{
            return ResponseEntity.success("车位禁用失败！");
        }
    }

    //启用
    @RequestMapping("/delqCar")
    @ResponseBody
    public ResponseEntity delqCar(@RequestParam("id")String id){
        CarPort carPort = carPortService.getById(id);
        carPort.setCartportStatus(1);
        carPort.setDelFlag(false);
        boolean flag = carPortService.saveOrUpdate(carPort);
        if(flag){
            return ResponseEntity.success("车位启用成功！");
        }else {
            return ResponseEntity.success("车位启用失败！");
        }
    }



    @Resource
    private ClientMessageService clientMessageService;

    //跳转车位绑定页面  查询车位类型表
    //查询车位编号与车位类型
    @RequestMapping("/addgbtncar")
    public String addgbtncar(CarPort carPort,Model model){
        CarPort portInfo = carPortService.getCarPortById(carPort);
        List<ClientMessage> clientMessages = clientMessageService.list(null);
        model.addAttribute("portInfo",portInfo);
        model.addAttribute("clientMessages",clientMessages);
        return "addgbtncar";
    }

    //执行车位绑定
    @RequestMapping("/upBtn")
    @ResponseBody
    public ResponseEntity upBtn(CarPort carPort){
        ClientMessage clientMessage = clientMessageService.getById(carPort.getClientMessageId());
        //将当前车位与客户对象进行关联
        clientMessage.setCarPortId(carPort.getId());
        //调用IService接口中预定义的updateById方法，实现修改操作。
        boolean flag = clientMessageService.updateById(clientMessage);
        if (flag) {
            carPort.setCartportStatus(1);
            carPortService.updateById(carPort);  //修改车位的状态为1
            return ResponseEntity.success("车位绑定成功！");
        } else {
            return ResponseEntity.success("车位绑定失败！");
        }
    }

    //修改时查询
    @RequestMapping("/selcUp")
    public String selcUp(CarPort car,Model model){
        CarPort carPort = carPortService.getById(car);
        List<CarportType> typeList = carPortTypeService.list(null);
        model.addAttribute("carPort",carPort);
        model.addAttribute("typeList",typeList);
        return "gupdatecar";
    }

    //保存对车位信息的修改操作
    @PostMapping("/upCar")
    @ResponseBody
    public ResponseEntity upCar(CarPort carPort){
        boolean flag = carPortService.saveOrUpdate(carPort);
        if(flag){
            return ResponseEntity.success("车位信息编辑成功！");
        } else {
            return ResponseEntity.success("车位信息编辑失败！");
        }
    }


}
