package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

public interface UserService extends IService<User> {
    User findUserByLoginName(String loginName);

    String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    User findUserById(String userId);

    boolean userCount(String email);
    
    User saveUser(User user);

    void saveUserRoles(String id, Set<Role> roleLists);

    void deleteUser(User u);

    User updateUser(User user);
}
