package com.yunhan.service;

import com.yunhan.entity.Receivable;
import com.yunhan.entity.pageCount;

import java.util.List;
import java.util.Map;

public interface ReceivableService {
    //查询所有数据
    /*List<Receivable> selectAll(Integer page, Integer limit, Receivable receivable);*/
    List<Map> selectAll(pageCount pageCount, Receivable receivable);
    //查询记录数
    int selectCount(Receivable receivable);
}
