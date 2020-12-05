package com.yunhan.mapper.receivable;

import com.yunhan.entity.Receivable;
import com.yunhan.entity.Receivables;

import java.util.List;
import java.util.Map;

public interface ReceivablesMapper {
    //查询所有数据
    List<Receivables> selectAll(Map<String, Object> map);
    //查询记录数
    Long selectCount(Receivables receivables);
}
