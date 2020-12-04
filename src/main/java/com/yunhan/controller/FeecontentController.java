package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunhan.common.base.PageData;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.Bill;
import com.yunhan.entity.ClientMessage;
import com.yunhan.entity.Feecontent;
import com.yunhan.entity.Room;
import com.yunhan.service.BillService;
import com.yunhan.service.ClientMessageService;
import com.yunhan.service.FeecontentService;
import com.yunhan.service.RoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Controller
public class FeecontentController {
    @Resource
    private FeecontentService feecontentService;
    @Resource
    private ClientMessageService clientMessageService;
    @Resource
    private RoomService roomService;
    @Resource
    private BillService billService;

    //负责跳转到feereport.html收费列表页面
    @RequestMapping("/feereports")
    public String feereport(){
        return "feereport";
    }

    @RequestMapping("/selcol")
    @ResponseBody
    public PageData<Feecontent> listDate(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
            Feecontent feecontent){
        PageData<Feecontent> feecontentPageData = new PageData<Feecontent>();
        Long count = feecontentService.getCountFeeListPage(feecontent);
        //2、调用service的getFeeListPage方法，实现分页查询收费项目列表信息
        List<Feecontent> payList= feecontentService.getFeeListPage(page, limit, feecontent);
        feecontentPageData.setData(payList);
        feecontentPageData.setCount(count);
        return feecontentPageData;
    }

    @RequestMapping("addFeereport")
    public String addFeereport(Model model){
        List<ClientMessage> client = clientMessageService.list(null);
        List<Room> rooms = roomService.list(null);
        model.addAttribute("client",client);
        model.addAttribute("rooms",rooms);
        return "addFeereport";
    }

    @PostMapping("addFee")
    @ResponseBody
    public ResponseEntity addFee(Feecontent feecontent){
        Bill bill = new Bill();
        if(StringUtils.isBlank(feecontent.getRoomID())){
            return ResponseEntity.failure("房间编号不能为空");
        }
        if(StringUtils.isBlank(feecontent.getPayproject())){
            return ResponseEntity.failure("收费项目不能为空");
        }
        Room room = roomService.getRoomListByRoomId(feecontent.getRoomID());
        room.setClientMessageId(feecontent.getClientMessageId());
        room.setId(UUID.randomUUID()+"");

        bill.setRoomID(room.getId());
        bill.setPayproject(feecontent.getPayproject());
        if(billService.findBillByRoomIdAndPayproject(bill)>0){
            return ResponseEntity.failure("收费项目已存在");
        }else {
            roomService.save(room);
            billService.addBill(bill);
            feecontentService.addFee(feecontent);
        }
        return ResponseEntity.success("新增成功");
    }

    //跳转到查询页面
    @RequestMapping("/jiaofei")
    public String payment(){
        return "payment";
    }

    //查询列表
    @RequestMapping("/sell")
    @ResponseBody
    public PageData<Feecontent> feecontentlist(
            @RequestParam(value = "page" , defaultValue = "1") Integer page ,
            @RequestParam(value = "limit" , defaultValue = "10") Integer limit , Feecontent feecontent){

        PageData<Feecontent> paymentPageData = new PageData<>();
        //1、获取分页查询时的总记录数
        Long count = feecontentService.getCountFeeListPage(feecontent);
        //2、调用service的getFeecontent方法,实现分页查询缴费项目列表信息
        List<Feecontent> feecontentList = feecontentService.getFeeListPage(page , limit , feecontent);
        paymentPageData.setData(feecontentList);
        paymentPageData.setCount(count);
        return paymentPageData;
    }

    /*@RequestMapping("selOne")
    @ResponseBody
    public String updateFee(@RequestParam(value = "roomid",required = false)Integer feeID,Model model) {
        List<Feecontent> chargemsg = feecontentService.getFeeListByFeeID(feeID);
        model.addAttribute("chargemsg",chargemsg);
        return "yaddcharge";
    }*/

    @RequestMapping("delete")
    @ResponseBody
    public ResponseEntity deleteFee(@RequestParam(value = "feeID",required = false)Integer feeID) {
        if(feecontentService.deleteFee(feeID)){
            return ResponseEntity.success("删除成功");
        }else {
            return ResponseEntity.failure("删除失败");
        }
    }
}
