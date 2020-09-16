package com.example.springbootv5.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootv5.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName： UserMapper
 * @Description： TODO
 * @Date： 2020/7/31 4:49 下午
 * @author： ZhuFangTao
 */
@Mapper
public interface UserMapper {

    /**
     * 功能描述: <br>
     * 〈根据用户名和密码查询用户〉
     * @Param: [username, password]
     * @Return: com.example.springbootv5.pojo.User
     * @Author: zft
     * @Date: 2020/7/31 4:51 下午
     */
    User selectUserByUserNameAndPassword(JSONObject paramsJson);

    /**
     * 功能描述: <br>
     * 〈添加用户〉
     * @Param: [user]
     * @Return: java.lang.Integer
     * @Author: zft
     * @Date: 2020/8/7 5:00 下午
     */
    Integer addUser(User user);

    /**
     * 功能描述: <br>
     * 〈账户转出〉
     * @Param: [moveOutId, moveOutMoney]
     * @Return: java.lang.Integer
     * @Author: zft
     * @Date: 2020/8/7 5:57 下午
     */
    Integer moveMoneyOut(@Param("moveOutId") Integer moveOutId, @Param("moveOutMoney") BigDecimal moveOutMoney);

    /**
     * 功能描述: <br>
     * 〈账户转入〉
     * @Param: [moveInId, moveInMoney]
     * @Return: java.lang.Integer
     * @Author: zft
     * @Date: 2020/8/7 6:01 下午
     */
    Integer moveMoneyIn(@Param("moveInId") Integer moveInId, @Param("moveInMoney") BigDecimal moveInMoney);

    /**
     * 功能描述: <br>
     * 〈查询用户列表〉
     * @Param: [jsonParams]
     * @Return: java.util.List<com.example.springbootv5.pojo.User>
     * @Author: zft
     * @Date: 2020/8/10 10:44 上午
     */
    List<User> userList(JSONObject jsonParams);

    /**
     * 功能描述: <br>
     * 〈根据用户id查询用户〉
     * @Param: [id]
     * @Return: com.example.springbootv5.pojo.User
     * @Author: zft
     * @Date: 2020/8/17 10:40 上午
     * @param id
     */
    User userDetail(Integer id);
}
