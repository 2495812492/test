package com.yunhan.service.impl;

import com.yunhan.entity.ComplaintSuggestion;
import com.yunhan.entity.RepairList;
import com.yunhan.mapper.repair.RepairListMapper;
import com.yunhan.service.RepairListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RepairListServiceImpl implements RepairListService {
    @Resource
    private RepairListMapper repairListMapper;

    @Override
    public List<RepairList> selectAll(Integer page, Integer limit, RepairList repairList) {
        Integer begin = (page - 1) * limit;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("begin", begin);
        map.put("end", limit);
        map.put("clientname",repairList.getClientname());
        return repairListMapper.selectAll(map);
    }

    @Override
    public Long selectCount(RepairList repairList) {
        return repairListMapper.selectCount(repairList);
    }

    @Override
    public int addBtn(RepairList repairList) {
        return repairListMapper.addBtn(repairList);
    }

    @Override
    public List<RepairList> cselRooms(RepairList repairList) {
        return repairListMapper.cselRooms(repairList);
    }

    @Override
    public RepairList selectRepairListById(Integer repairListId) {
        return repairListMapper.selectRepairListById(repairListId);
    }

    @Override
    public void UpdateSl(RepairList repairList) {
        repairListMapper.UpdateSl(repairList);
    }

    @Override
    public RepairList RepairListById2(Integer repairListId) {
        return repairListMapper.RepairListById2(repairListId);
    }

    @Override
    public void UpdateSf(RepairList repairList) {
        repairListMapper.UpdateSf(repairList);
    }

    @Override
    public List<ComplaintSuggestion> selectUser(ComplaintSuggestion complaintSuggestion) {
        return repairListMapper.selectUser(complaintSuggestion);
    }

    @Override
    public RepairList selectRepairListById3(Integer repairListId) {
        return repairListMapper.selectRepairListById3(repairListId);
    }

    @Override
    public void UpdateYwwc(RepairList repairList) {
        repairListMapper.UpdateYwwc(repairList);
    }

    @Override
    public RepairList selectRepairListById4(Integer repairListId) {
        return repairListMapper.selectRepairListById4(repairListId);
    }

    @Override
    public void Delete(Integer repairListId) {
        repairListMapper.Delete(repairListId);
    }
}
