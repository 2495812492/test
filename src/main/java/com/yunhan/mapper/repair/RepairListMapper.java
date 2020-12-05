package com.yunhan.mapper.repair;

import com.yunhan.entity.ComplaintSuggestion;
import com.yunhan.entity.RepairList;
import java.util.List;
import java.util.Map;

public interface RepairListMapper {
    //查询所有数据
    List<RepairList> selectAll(Map<String, Object> map);
    //查询分页记录
    Long selectCount(RepairList repairList);
    //添加
    int addBtn(RepairList repairList);
    //查询房间
    List<RepairList> cselRooms(RepairList repairList);
    //受理&派工
    RepairList selectRepairListById(Integer repairListId);
    //修改受理&派工
    void UpdateSl(RepairList repairList);
    //收费
    RepairList RepairListById2(Integer repairListId);
    //修改收费
    void UpdateSf(RepairList repairList);
    //查询管理员
    public List<ComplaintSuggestion> selectUser(ComplaintSuggestion complaintSuggestion);
    //业务完成
    RepairList selectRepairListById3(Integer repairListId);
    //修改业务完成
    void UpdateYwwc(RepairList repairList);
    //详情
    RepairList selectRepairListById4(Integer repairListId);
    //删除
    void Delete(Integer repairListId);
}
