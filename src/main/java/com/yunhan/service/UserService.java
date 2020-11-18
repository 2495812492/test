package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.User;

public interface UserService extends IService<User> {
    User findUserByLoginName(String loginName);
}
