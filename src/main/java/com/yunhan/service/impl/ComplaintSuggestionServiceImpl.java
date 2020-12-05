package com.yunhan.service.impl;

import com.yunhan.entity.ComplaintSuggestion;
import com.yunhan.mapper.complaintSuggestion.ComplaintSuggestionMapper;
import com.yunhan.service.ComplaintSuggestionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintSuggestionServiceImpl implements ComplaintSuggestionService {
    @Resource
    private ComplaintSuggestionMapper comMapper;

    @Override
    public List<ComplaintSuggestion> selectAll(Integer page, Integer limit, ComplaintSuggestion complaintSuggestion) {
        Integer begin = (page - 1) * limit;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("begin", begin);
        map.put("end", limit);
        map.put("complainant",complaintSuggestion.getComplainant());
        return comMapper.selectAll(map);
    }

    @Override
    public Long selectCount(ComplaintSuggestion com) {
        return comMapper.selectCount(com);
    }

    @Override
    public int add(ComplaintSuggestion com) {
        return comMapper.add(com);
    }

    @Override
    public List<ComplaintSuggestion> cselRooms(ComplaintSuggestion complaintSuggestion) {
        return comMapper.cselRooms(complaintSuggestion);
    }

    @Override
    public List<ComplaintSuggestion> selectUser(ComplaintSuggestion complaintSuggestion) {
        return comMapper.selectUser(complaintSuggestion);
    }

    @Override
    public ComplaintSuggestion selectComplaintSuggestionById(Integer complaintSuggestionId) {
        return comMapper.selectComplaintSuggestionById(complaintSuggestionId);
    }

    @Override
    public void UpdateComplaint(ComplaintSuggestion complaintSuggestion) {
        comMapper.UpdateComplaint(complaintSuggestion);
    }

    @Override
    public ComplaintSuggestion selectComplaintSuggestionById2(Integer complaintSuggestionId) {
        return comMapper.selectComplaintSuggestionById2(complaintSuggestionId);
    }

    @Override
    public void UpdateComplaint2(ComplaintSuggestion complaintSuggestion) {
            comMapper.UpdateComplaint2(complaintSuggestion);
    }

    @Override
    public int del(Integer complaintSuggestionId) {
        return comMapper.del(complaintSuggestionId);
    }


}
