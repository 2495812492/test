package com.yunhan.mapper.complaintSuggestion;

import com.yunhan.entity.ComplaintSuggestion;

import java.util.List;
import java.util.Map;

public interface ComplaintSuggestionMapper {
    //查询所有数据
    List<ComplaintSuggestion> selectAll(Map<String, Object> map);
    //查询分页记录
    Long selectCount(ComplaintSuggestion com);
    //添加数据
    int add(ComplaintSuggestion com);
    //查询房间
    public List<ComplaintSuggestion> cselRooms(ComplaintSuggestion complaintSuggestion);
    //查询管理员
    public List<ComplaintSuggestion> selectUser(ComplaintSuggestion complaintSuggestion);
    //受理
    ComplaintSuggestion selectComplaintSuggestionById(Integer complaintSuggestionId);
    //修改受理
    void UpdateComplaint(ComplaintSuggestion complaintSuggestion);
    //回访
    ComplaintSuggestion selectComplaintSuggestionById2(Integer complaintSuggestionId);
    //修改回访
    void UpdateComplaint2(ComplaintSuggestion complaintSuggestion);
    //删除
    int del(Integer complaintSuggestionId);
}
