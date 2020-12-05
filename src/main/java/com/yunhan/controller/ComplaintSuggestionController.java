package com.yunhan.controller;

import com.yunhan.entity.*;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.ui.Model;
import com.yunhan.common.base.PageData;
import com.yunhan.entity.ComplaintSuggestion;
import com.yunhan.service.ComplaintSuggestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
/**
 * 投诉建议
 */
public class ComplaintSuggestionController {
    @Resource
    private ComplaintSuggestionService completionService;

    @RequestMapping("complaintSuggestion")
    public String ComplaintSuggestion(){
        return "complaintSuggestion";
    }

    @RequestMapping("/listData2")
    @ResponseBody
    public PageData<ComplaintSuggestion>listData(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
             @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            ComplaintSuggestion complaintSuggestion){
        PageData<ComplaintSuggestion> comDate = new PageData<ComplaintSuggestion>();
        Long count = completionService.selectCount(complaintSuggestion);
        List<ComplaintSuggestion> comList = completionService.selectAll(page,limit,complaintSuggestion);
        comDate.setData(comList);
        comDate.setCount(count);
        return comDate;
    }

    //添加数据
    @RequestMapping("/addComplaint")
    public String addComplaint(ComplaintSuggestion complaintSuggestion,Model model){
        List<ComplaintSuggestion> rcs=completionService.cselRooms(complaintSuggestion);
        model.addAttribute("rcs",rcs);
        return "addComplaint";
    }

    @RequestMapping("/add_do2")
    @ResponseBody
    public tableResult<ComplaintSuggestion> add_do2(ComplaintSuggestion complaintSuggestion){
        completionService.add(complaintSuggestion);
        tableResult result = new tableResult();
        //添加成功页面显示
        result.setMsg("添加成功");
        return result;
    }

    //受理
    @RequestMapping("/selectComplaintSuggestionById")
    public String SelectComplaintSuggestionById(Integer complaintSuggestionId,Model model){
        ComplaintSuggestion complaintSuggestion = completionService.selectComplaintSuggestionById(complaintSuggestionId);
        model.addAttribute("complaintSuggestion",complaintSuggestion);
        List<ComplaintSuggestion> nName = completionService.selectUser(complaintSuggestion);
        model.addAttribute("nName",nName);
        return "updateComplaint";
    }

    //修改受理
    @RequestMapping("/updateComplaint")
    @ResponseBody
    public tableResult<ComplaintSuggestion> updateComplaint(ComplaintSuggestion complaintSuggestion){
        completionService.UpdateComplaint(complaintSuggestion);
        tableResult result = new tableResult();
        result.setMsg("修改成功");
        return result;
    }

    //回访
    String [] list=new String[]{"很不满意","不满意","一般","满意","非常满意"};
    @RequestMapping("/selectComplaintSuggestionById2")
    public String SelectComplaintSuggestionById2(Integer complaintSuggestionId,Model model){
        ComplaintSuggestion complaintSuggestion = completionService.selectComplaintSuggestionById2(complaintSuggestionId);
        System.out.println(complaintSuggestion.getDegreeofsatisfaction());
        List<message> list2=new ArrayList<message>();
        for(int i=0;i<list.length;i++){
            if(!complaintSuggestion.getDegreeofsatisfaction().equals(list[i])){
                message o=new message();
                o.setValue(list[i]);
                list2.add(o);
            }
        }
        model.addAttribute("complaintSuggestion",complaintSuggestion);
        List<ComplaintSuggestion> nName = completionService.selectUser(complaintSuggestion);
        model.addAttribute("nName",nName);
        model.addAttribute("list",list2);
        return "updateComplaint2";
    }

    //修改回访
    @RequestMapping("/updateComplaint2")
    @ResponseBody
    public tableResult<ComplaintSuggestion> updateComplaint2(ComplaintSuggestion complaintSuggestion){
        completionService.UpdateComplaint2(complaintSuggestion);
        tableResult result = new tableResult();
        result.setMsg("修改成功");
        return result;
    }

    //删除
    @RequestMapping("/delete2")
    @ResponseBody
    public tableResult<ComplaintSuggestion> delete2(Integer complaintSuggestionId){
        completionService.del(complaintSuggestionId);
        tableResult result=new tableResult();
        //删除提示
        result.setMsg("删除成功！");
        return  result;
    }
}
