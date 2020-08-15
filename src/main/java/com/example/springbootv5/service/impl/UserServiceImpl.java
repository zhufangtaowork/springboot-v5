package com.example.springbootv5.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootv5.common.ResultCode;
import com.example.springbootv5.mapper.UserMapper;
import com.example.springbootv5.pojo.User;
import com.example.springbootv5.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName： UserServiceImpl
 * @Description： 用户接口实现类
 * @Date： 2020/7/31 4:44 下午
 * @author： ZhuFangTao
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User selectUserByUserNameAndPassword(JSONObject paramsJson) {
        User user = userMapper.selectUserByUserNameAndPassword(paramsJson);
        if (Optional.ofNullable(user).isPresent()){
            return user;
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        Integer i = userMapper.addUser(user);
        return i>0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveMoney(JSONObject jsonParams) {
        Integer moveOutId = jsonParams.getInteger("moveOutId");
        BigDecimal moveOutMoney = jsonParams.getBigDecimal("moveOutMoney");
        Integer moveInId = jsonParams.getInteger("moveInId");
        if (moveOutId==null || moveOutMoney==null || moveInId==null){
            return false;
        }
        Integer i = userMapper.moveMoneyOut(moveOutId,moveOutMoney);
        Integer p = userMapper.moveMoneyIn(moveInId,moveOutMoney);
        if (i>0 && p>0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<User> userList(JSONObject jsonParams) {
        Integer page = jsonParams.getInteger("page")==null ? ResultCode.DEFAULT_PAGE:jsonParams.getInteger("page");
        Integer pageSize = jsonParams.getInteger("pageSize")==null ? ResultCode.DEFAULT_PAGE_SIZE:jsonParams.getInteger("page");
        PageHelper.startPage(page,pageSize);
        List<User> userList = userMapper.userList(jsonParams);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

}
