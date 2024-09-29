package com.itciod.goosesystem.controller;

import com.itciod.goosesystem.pojo.Result;
import com.itciod.goosesystem.pojo.User;
import com.itciod.goosesystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 */
@Slf4j
@RestController
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * 查询用户数据
     * @return
     */
    @RequestMapping("/users")
    public Result list(){
        log.info("查询全部用户数据");

        //调用service查询用户数据
        List<User> userList = userService.list();

        return Result.success(userList);
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门:{}", id);
        //调用service删除用户数据
        userService.delete(id);
        return Result.success();
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/users")
    public Result add(@RequestBody User user){
        log.info("新增用户:{}", user);
        //调用service添加用户数据
        userService.add(user);
        return Result.success();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping("/users")
    public Result update(@RequestBody User user){
        log.info("更新用户信息:{}",user);
        userService.update(user);
        return Result.success();
    }


    @PutMapping("/users/batch-select")
    public Result batchSelect(@RequestBody Map<String, List<Integer>> request)
    {
        log.info("批量查询用户信息:{}",request);
        List<Integer> ids = request.get("ids");
        userService.batchSelect(ids);
        return Result.success();

    }

    @DeleteMapping("/users/batch-delete")
    public Result batchDelete(@RequestBody Map<String, List<Integer>> request)
    {
        log.info("批量删除用户信息:{}",request);
        List<Integer> ids = request.get("ids");
        userService.batchDelete(ids);
        return Result.success();

    }





}
