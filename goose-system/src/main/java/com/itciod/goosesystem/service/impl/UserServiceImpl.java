package com.itciod.goosesystem.service.impl;



import com.itciod.goosesystem.mapper.UserMapper;
import com.itciod.goosesystem.pojo.Enum.Androidstaue;
import com.itciod.goosesystem.pojo.User;
import com.itciod.goosesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void add(User user) {
        user.setAndroidstaue(Androidstaue.NOT_ACTIVATED);//移动端激活状态默认设置为未激活

        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public List<User> batchSelect(List<Integer> ids) {
        return userMapper.batchSelectFromJava(ids);
    }

    @Override
    public void batchDelete(List<Integer> ids)
    {
        userMapper.batchDeleteFromJava(ids);
    }

}
