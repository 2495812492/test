package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.Bill;

public interface BillService extends IService<Bill> {
    Integer findBillByRoomIdAndPayproject(Bill bill);
    Bill addBill(Bill bill);
}
