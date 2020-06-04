package com.ccut.bbs.service;

import com.ccut.bbs.mapper.UserMapper;
import com.ccut.bbs.model.User;
import com.ccut.bbs.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //判断用户是否存在的逻辑，存在则插入，不存在则更新
    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis()); //用当前的毫秒数传进去
            user.setGmtModified(user.getGmtCreate()); //在create里获取传进去的毫秒值
            userMapper.insert(user);
        }else {
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis()); //更改的时候传入当前系统时间
            updateUser.setAvatarUrl(user.getAvatarUrl()); //更新的时候传入最近的头像 因为头像可能会改变
            updateUser.setName(user.getName()); //更新的时候传入新的名字
            updateUser.setToken(user.getToken()); //更新的时候token会变化
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
