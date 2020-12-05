package com.yunhan.service;

import com.yunhan.entity.Receivable;
import com.yunhan.entity.Receivables;

import java.util.List;

public interface ReceivablesService {
    //查询所有数据
    List<Receivables> selectAll(Integer page, Integer limit, Receivables receivables);
    //查询记录数
    Long selectCount(Receivables receivables);
}
