package com.yunhan.controller;

import com.yunhan.common.base.PageData;
import com.yunhan.entity.ComplaintSuggestion;
import com.yunhan.entity.RepairList;
import com.yunhan.entity.tableResult;
import com.yunhan.service.RepairListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/*
    报修工单
 */
@Controller
public class RepairListController {
    @Resource
    private RepairListService repairListService;

    @RequestMapping("repairList")
    public String RepairList(){
        return "repairList";
    }

    //分页显示
    @RequestMapping("/listDataRepair")
    @ResponseBody
    public PageData<RepairList> listDate(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                         RepairList repairList){
        PageData<RepairList> repairData = new PageData<RepairList>();
        Long count = repairListService.selectCount(repairList);
        List<RepairList> repairLists = repairListService.selectAll(page,limit,repairList);
        repairData.setData(repairLists);
        repairData.setCount(count);
        return repairData;
    }

    @RequestMapping("/addRepair")
    public String add(RepairList repairList, Model model){
        List<RepairList> rc = repairListService.cselRooms(repairList);
        model.addAttribute("rc",rc);
         return "addRepair";
    }

    @RequestMapping("/addRepairList")
    @ResponseBody
    public tableResult<RepairList> AddBtn(RepairList repairList){
        repairListService.addBtn(repairList);
        tableResult result = new tableResult();
        //添加成功页面显示
        result.setMsg("添加成功");
        return result;
    }

    //受理&派工
    @RequestMapping("/selectRepairListById")
    public String SelectRepairListById(Integer repairListId,Model model,ComplaintSuggestion complaintSuggestion){
        RepairList repairList = repairListService.selectRepairListById(repairListId);
        model.addAttribute("repairList",repairList);
        List<ComplaintSuggestion> nname=repairListService.selectUser(complaintSuggestion);
        model.addAttribute("nname",nname);
        return "repairSl";
    }

    //修改受理&派工
    @RequestMapping("/updateSl")
    @ResponseBody
    public tableResult<RepairList> updateSl(RepairList repairList){
        repairListService.UpdateSl(repairList);
        tableResult result = new tableResult();
        result.setMsg("修改成功");
        return result;
    }


    //收费
    @RequestMapping("/selectRepairListById2")
    public String RepairListById(Integer repairListId,Model model,ComplaintSuggestion complaintSuggestion){
        RepairList repairList = repairListService.RepairListById2(repairListId);
        model.addAttribute("repairList",repairList);
        List<ComplaintSuggestion> nname=repairListService.selectUser(complaintSuggestion);
        model.addAttribute("nname",nname);
        return "repairSf";
    }

    //修改收费
    @RequestMapping("/updateSf")
    @ResponseBody
    public tableResult<RepairList> updateSf(RepairList repairList){
        repairListService.UpdateSf(repairList);
        tableResult result = new tableResult();
        result.setMsg("修改成功");
        return result;
    }

    //业务完成
    @RequestMapping("/selectRepairListById3")
    public String SelectRepairListById3(Integer repairListId,Model model,ComplaintSuggestion complaintSuggestion){
        RepairList repairList = repairListService.selectRepairListById3(repairListId);
        model.addAttribute("repairList",repairList);
        List<ComplaintSuggestion> nname=repairListService.selectUser(complaintSuggestion);
        model.addAttribute("nname",nname);
        return "repairYwwc";
    }

    //修改业务完成
    @RequestMapping("/updateYwwc")
    @ResponseBody
    public tableResult<RepairList> updateYwwc(RepairList repairList){
        repairListService.UpdateYwwc(repairList);
        tableResult result = new tableResult();
        result.setMsg("修改成功");
        return result;
    }

    //详情
    @RequestMapping("/selectRepairListById4")
    public String SelectRepairListById4(Integer repairListId,Model model){
        RepairList repairList = repairListService.selectRepairListById4(repairListId);
        model.addAttribute("repairList",repairList);
        return "repairXq";
    }

    //删除
    @RequestMapping("/deleteRepairList")
    @ResponseBody
    public tableResult<RepairList> deleteRepairList(Integer repairListId){
        repairListService.Delete(repairListId);
        tableResult result = new tableResult();
        result.setMsg("删除成功");
        return result;
    }
}
