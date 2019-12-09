package spring17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring17.mapper.UserMapper;
import spring17.model.User;

/**
 * Author:ShiQi
 * Date:2019/12/10-1:18
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user){
        User dbUser = userMapper.findByAccountID(user.getAccountId());
        long nowTime = System.currentTimeMillis();
        if(dbUser==null){
            //插入
            //当前用户总数：
            Integer userNum=userMapper.count();
            user.setId(userNum+1);//手动自增（勉为其难.jpg）
            //注册时间
            user.setGmtCreate(nowTime);
            user.setGmtModified(nowTime);
            userMapper.insert(user);
        }else{
            //更新
            user.setGmtModified(nowTime);
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}