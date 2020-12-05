package com.yunhan.mapper.receivable;

import com.yunhan.entity.Receivable;

import java.util.List;
import java.util.Map;

public interface ReceivableMapper {
    //查询所有数据
    /*List<Receivable> selectAll(Map<String, Object> map);*/
    List<Map> selectAll(Map<String, Object> map);
    //查询记录数
    int selectCount(Receivable receivable);
}
