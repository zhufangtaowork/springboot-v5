package com.example.springbootv5.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootv5.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName： UserService
 * @Description： 用户接口
 * @Date： 2020/7/31 4:43 下午
 * @author： ZhuFangTao
 */
public interface UserService {

    /**
     * 功能描述: <br>
     * 〈查询用户〉
     * @Param: [paramsJson]
     * @Return: com.example.springbootv5.pojo.User
     * @Author: zft
     * @Date: 2020/8/6 7:29 下午
     */
    User selectUserByUserNameAndPassword(JSONObject paramsJson);

    /**
     * 功能描述: <br>
     * 〈添加用户〉
     * @Param: [user]
     * @Return: boolean
     * @Author: zft
     * @Date: 2020/8/7 4:57 下午
     */
    boolean addUser(User user);

    /**
     * 功能描述: <br>
     * 〈转账接口〉
     * @Param: [jsonParams]
     * @Return: boolean
     * @Author: zft
     * @Date: 2020/8/7 6:13 下午
     */
    boolean moveMoney(JSONObject jsonParams);

    /**
     * 功能描述: <br>
     * 〈查询用户列表〉
     * @Param: [jsonParams]
     * @Return: java.util.List<com.example.springbootv5.pojo.User>
     * @Author: zft
     * @Date: 2020/8/10 10:19 上午
     * @return
     */
    PageInfo<User> userList(JSONObject jsonParams);

    /**
     * 功能描述: <br>
     * 〈根据id查询用户〉
     * @Param: [id]
     * @Return: com.example.springbootv5.pojo.User
     * @Author: zft
     * @Date: 2020/8/17 10:39 上午
     * @param id
     */
    User userDetail(Integer id);
}
