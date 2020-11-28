package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunhan.common.base.PageData;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.GmdLou;
import com.yunhan.entity.User;
import com.yunhan.service.GmdLouService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class GmdLouController {

    @RequestMapping("glou")
    public String glou(){
        return "glou";      //跳转到templates/glou.html楼号列表页面
    }


    @Resource
    private GmdLouService gmdLouService;

    /**
     * 分页查询楼号列表信息
     * @param page 当前页码
     * @param limit 每页显示的数据行数
     */
    @RequestMapping("/gloulistDate")
    @ResponseBody
    public PageData<GmdLou> list(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,GmdLou gmdLou){
        QueryWrapper<GmdLou> wrapper = new QueryWrapper<GmdLou>();
        if(gmdLou != null && StringUtils.isNotEmpty(gmdLou.getBuildingName())){
            wrapper.like("buildingName",gmdLou.getBuildingName());
        }

        IPage<GmdLou> gmdLouIPage = gmdLouService.page(new Page<>(page,limit),wrapper);
        PageData<GmdLou> pageData = new PageData<>();
        pageData.setCount(gmdLouIPage.getTotal());
        pageData.setData(gmdLouIPage.getRecords()); //gmdLouIPage.getRecords()得到分页查询出来的楼号信息列表
        return pageData;
    }



    @RequestMapping("/addglou")
    public String add(){
        return "addglou";   //返回addglou.html页面的逻辑视图名，实现跳转到此页面
    }

    //添加处理
    @PostMapping("/addLou")
    @ResponseBody
    public ResponseEntity addLou(GmdLou gmdLou){
        QueryWrapper<GmdLou> wrapper = new QueryWrapper<>();
        wrapper.eq("buildingName",gmdLou.getBuildingName());
        if(gmdLouService.count(wrapper) > 0){
            return ResponseEntity.failure("当前楼号已存在，不可重复添加！");
        }else{
            boolean flag = gmdLouService.save(gmdLou);//新增
            if(flag){
                return ResponseEntity.success("楼号信息添加成功！");
            }else{
                return ResponseEntity.failure("楼号信息添加失败！");
            }
        }
    }

    //修改
    @RequestMapping("/selbuildingid")
    public String selbuildingid(@RequestParam("id")String id, Model model){
        GmdLou gmdLou = gmdLouService.getById(id);
        model.addAttribute("gl",gmdLou);//跳转到楼号修改页面
        return "gupdatelou";
    }

    //修改
    @PostMapping("/upLou")
    @ResponseBody
    public ResponseEntity upLou(GmdLou gmdLou){
        //获取当前登录的用户对象
        User user = Encodes.getLoginUser();
        gmdLou.setUpdateId(user.getId());   //设置修改者ID值
        //实现保存对楼号信息的修改操作
        //两种方式实现
        // 1、使用mybatis自定义的upLou方法   2、使用IService接口中预定义的updateById方法

        int result = gmdLouService.upLou(gmdLou);
        // boolean flag = gmdLouService.updateById(gmdLou);  这个是使用mybatiplus自带的
        if(result > 0){
            return ResponseEntity.success("修改成功！");
        }else{
            return ResponseEntity.success("修改失败！");
        }
    }

    // 根据楼号id禁用楼号信息 (这里是逻辑删除，只需要修改楼号对象的buildingStatus值为1即可)
    @RequestMapping("/delLou")
    @ResponseBody
    public ResponseEntity delLou(GmdLou lou){
        User user = Encodes.getLoginUser();
        lou.setUpdateId(user.getId());
        lou.setBuildingStatus(1);
        int result = gmdLouService.upLou(lou);
        if(result > 0){
            return ResponseEntity.success("禁用楼号信息成功！");
        }else {
            return ResponseEntity.success("禁用楼号信息失败！");
        }
    }

    @RequestMapping("/delqLou")
    @ResponseBody
    public ResponseEntity delqLou(GmdLou lou){
        User user = Encodes.getLoginUser();
        lou.setUpdateId(user.getId());
        lou.setBuildingStatus(0);
        int result = gmdLouService.upLou(lou);
        if(result > 0){
            return ResponseEntity.success("启用楼号信息成功！");
        }else {
            return ResponseEntity.success("启用楼号信息失败！");
        }
    }



}
