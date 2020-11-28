package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yunhan.common.base.PageData;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.ClientMessage;
import com.yunhan.entity.Educations;
import com.yunhan.entity.GmdLou;
import com.yunhan.service.ClientMessageService;
import com.yunhan.service.EducationService;
import com.yunhan.service.GmdLouService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public class ClientMessageController {

    @Resource
    private ClientMessageService clientMessageService;

    @Resource
    private EducationService educationService;

    @RequestMapping("/gclient")
    public String gclient(){
        return "gclient";//跳转到客户档案列表页面
    }

    /**
     * 分页查询客户档案列表信息
     * @param page  当前页码
     * @param limit 每页显示的数据行数
     */
    @RequestMapping("gclistData")
    @ResponseBody
    public PageData<ClientMessage> listData(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,ClientMessage clientMessage){
        PageData<ClientMessage> clientPageData  = new PageData<ClientMessage>();
        Long count = clientMessageService.getCountClientListPage(clientMessage);
        List<ClientMessage> clientList = clientMessageService.getClientListPage(page,limit,clientMessage);
        clientPageData.setCount(count);
        clientPageData.setData(clientList);
        return clientPageData;
    }


    //修改客户信息前查询，参数中使用clientMessage对象接收传递过来的id值
    @RequestMapping("/selUpClient")
    public String selUpClient(ClientMessage clientMessage, Model model){
        ClientMessage client = clientMessageService.getClientById(clientMessage.getId());
        List<Educations> educations = educationService.list(null);
        model.addAttribute("client",client);
        model.addAttribute("educations",educations );
        return "gupdateclient";
    }

    //执行保存修改后的客户信息
    @RequestMapping("/upClient")
    @ResponseBody
    public ResponseEntity upClient(ClientMessage clientMessage) {
        boolean flag = clientMessageService.saveOrUpdate(clientMessage);
        if (flag) {
            return ResponseEntity.success("客户信息修改成功！");
        } else {
            return ResponseEntity.failure("客户信息修改失败！");
        }
    }

    //添加处理/addgclient请求的方法
    @RequestMapping("/addgclient")
    public String addgclient(Model model){
        List<Educations> ed = educationService.list(null);
        model.addAttribute("ed",ed);
        return "addgclient";
    }

    //执行保存对新增用户的添加操作
    @RequestMapping("/addClient")
    @ResponseBody
    public ResponseEntity addClient(ClientMessage clientMessage){
        QueryWrapper<ClientMessage> wrapper = new QueryWrapper<ClientMessage>();
        wrapper.eq("clientname",clientMessage.getClientname());
        int count = clientMessageService.count(wrapper);
        if(count > 0){
            return ResponseEntity.failure("用户已存在！");
        }else{
            clientMessage.setCreateDate(new Date());
            boolean flag = clientMessageService.save(clientMessage);
            if(flag){
                return ResponseEntity.success("用户添加成功！");
            }else{
                return ResponseEntity.success("用户添加失败！");
            }
        }
    }


    //实现角色信息的删除
    @RequestMapping("/delClient")
    @ResponseBody
    public ResponseEntity delClient(@RequestParam("id")String id){
        ClientMessage client = clientMessageService.getClientById(id);
        client.setDelFlag(true);
        boolean flag = clientMessageService.saveOrUpdate(client);
        if(flag){
            return ResponseEntity.success("客户信息删除成功！");
        } else {
            return ResponseEntity.failure("客户信息删除失败！");
        }
    }

    //点击详情，查询客户详情对象信息
    @GetMapping("/selxqClient")
    public String selxqClient(@RequestParam("id")String id,Model model){
        ClientMessage client = clientMessageService.getClientById(id);
        List<Educations> educations = educationService.list(null);
        model.addAttribute("client",client);
        model.addAttribute("educations",educations);
        return "gclientxq";
    }


}
