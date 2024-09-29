package com.itciod.goosesystem.service;


import com.itciod.goosesystem.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询全部用户数据
     * @return
     */
    List<User> list();

    /**
     * 删除用户
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增用户
     * @param user
     */
    void add(User user);

    /**
     * 更新用户数据
     * @param user
     */
    void update(User user);


    /**
    * 批量查询用户
    * @param ids
    */
    List<User> batchSelect(@Param("ids") List<Integer> ids);

    /**
     * 批量删除用户
     * @param ids
     * */
    void batchDelete(@Param("ids") List<Integer> ids);
}
