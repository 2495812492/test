package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Bill;
import com.yunhan.mapper.billMapper.BillMapper;
import com.yunhan.service.BillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("billService")
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Override
    public Integer findBillByRoomIdAndPayproject(Bill bill) {
        return baseMapper.findBillByRoomIdAndPayproject(bill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bill addBill(Bill bill) {
        baseMapper.addBill(bill);
        return bill;
    }
}
