package spring17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring17.mapper.UserMapper;
import spring17.model.User;
import spring17.model.UserExample;

import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/10-1:18
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        long nowTime = System.currentTimeMillis();
        if(users.size()==0){
            //插入
            //当前用户总数：
            Integer userNum=userMapper.countByExample(new UserExample());
            user.setId(userNum+1);//手动自增（勉为其难.jpg）
            //注册时间
            user.setGmtCreate(nowTime);
            user.setGmtModified(nowTime);
            userMapper.insert(user);
        }else{
            //更新
            User dbUser = users.get(0);
            User updateUser=new User();
            updateUser.setGmtModified(nowTime);
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setBio(user.getBio());
            //更新局部：
            UserExample example = new UserExample();
            example.createCriteria()
                    .andAccountIdEqualTo(dbUser.getAccountId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
