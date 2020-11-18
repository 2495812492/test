package com.yunhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.User;
import com.yunhan.mapper.userMapper.UserMapper;
import com.yunhan.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User findUserByLoginName(String usrName) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("login_name",usrName);
        return userMapper.selectOne(wrapper);
    }

}
