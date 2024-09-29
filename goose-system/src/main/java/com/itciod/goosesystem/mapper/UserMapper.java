package com.itciod.goosesystem.mapper;

import com.itciod.goosesystem.pojo.User;
import com.itciod.goosesystem.provider.UserMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名和密码查询员工
     * @param user
     * @return
     */
    @Select("select * from goose_user_table where username=#{username} and password=#{password}  ")
    User getByUsernameAndPassword(User user);

    /**
     * 查询全部用户
     * @return
     */
    @Select("select * from goose_user_table")
    List<User> list();

    /**
     * 根据ID删除用户
     * @param id
     */
    @Delete("delete from goose_user_table where user_id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增用户
     * @param user
     */
    void insert(User user);

    /**
     * 更新员工
     * @param user
     */
    void update(User user);

    /**
     * 批量查询
     * @param ids
     **/
    @SelectProvider(type= com.itciod.goosesystem.provider.UserMapperProvider.class,method="batchSelect")
    List<User> batchSelectFromJava(@Param("ids")List<Integer> ids);

    /**
     * 批量删除
     * @param ids
     * */
    @SelectProvider(type= com.itciod.goosesystem.provider.UserMapperProvider.class,method="batchDelete")
    void batchDeleteFromJava(@Param("ids")List<Integer> ids);

}
