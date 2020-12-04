package com.yunhan.mapper.billMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Bill;

public interface BillMapper extends BaseMapper<Bill> {
    Integer findBillByRoomIdAndPayproject(Bill bill);
    void addBill(Bill bill);
}
