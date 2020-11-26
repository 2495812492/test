package com.yunhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.common.util.FileUtil;
import com.yunhan.entity.Rescource;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import com.yunhan.mapper.userMapper.UserMapper;
import com.yunhan.service.RescourceService;
import com.yunhan.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {
    @Resource
    private RescourceService rescourceService;

    @Override
    public User findUserByLoginName(String usrName) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("login_name",usrName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        byte[] data = file.getBytes();  //将要上传的文件对象转换成字节数组(内存中数据)
        Rescource rescource = null;
        QueryWrapper<Rescource> wrapper = new QueryWrapper<>();
        //通过字节输入流和文件大小两个参数得到其hash值。
        String hash = FileUtil.calcETag(file.getInputStream(),file.getSize());
        wrapper.eq("hash",hash);
        wrapper.eq("source","local");
        //调用selectOne方法实现通过hash和source两个查询条件到sys_rescource表中查询相关的数据，如果查询到了，就说明之前上传过，不再做上传操作，直接返回webUrl
        rescource = rescourceService.getOne(wrapper);
        if( rescource!= null){
            return rescource.getWebUrl();
        }
        //得到上传文件的后缀
        String extName = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        //使用UUID生成一个唯一的文件名字。
        String fileName = UUID.randomUUID() + extName;
        String contentType = file.getContentType();
        //获取当前项目的classpath的绝对路径
        StringBuffer sb = new StringBuffer(ResourceUtils.getURL("classpath:").getPath());
        //得到项目路径下的target/statis/upload/ 的绝对路径
        String filePath = sb.append("static/upload/").toString();
        //通过这个绝对路径创建File文件对象
        File targetFile = new File(filePath);
        //判断这个文件对象/目录不存在，则调用mkdis()方法创建出来。
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //创建字节输出对象
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(data); //将保存到内存中的data字节数组输出到输出流中写入到指定路径中—— filePath+fileName
        out.flush();
        out.close();
        String webUrl = "/static/upload/"+fileName;
        rescource = new Rescource();
        rescource.setFileName(fileName);
        rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize()/1024)+"kb");
        rescource.setHash(hash);
        rescource.setFileType(contentType);
        rescource.setWebUrl(webUrl);
        rescource.setSource("local");
        rescourceService.save(rescource); //实现向sys_resource表中新增一条记录。
        return webUrl;  //返回上传文件在项目中路径
    }

    @Override
    public User findUserById(String userId) {
        Map map = new HashMap();
        map.put("id",userId);
        return baseMapper.selectUserByMap(map);
    }

    @Override
    public boolean userCount(String str) {
        if(baseMapper.userCount(str)>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) {
        baseMapper.insert(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoles(String id, Set<Role> roleLists) {
        if(roleLists != null && roleLists.size() > 0){
            baseMapper.saveUserRoles(id, roleLists);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(User u) {
        u.setDelFlag(true);
        baseMapper.updateById(u);
        baseMapper.dropUserRolesByUserId(u.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        baseMapper.updateById(user);
        baseMapper.dropUserRolesByUserId(user.getId());
        if(user.getRoleLists() != null && user.getRoleLists().size() > 0){
            baseMapper.saveUserRoles(user.getId(),user.getRoleLists());
        }
        return user;
    }

}
