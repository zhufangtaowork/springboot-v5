package com.example.springbootv5.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootv5.common.ResultCode;
import com.example.springbootv5.common.ResultView;
import com.example.springbootv5.pojo.User;
import com.example.springbootv5.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * @ClassName： UserController
 * @Description： 用户模块
 * @Date： 2020/7/31 4:17 下午
 * @author： ZhuFangTao
 */
@Api(value = "UserController",tags = {"用户相关接口"})
@Slf4j
@RestController
@RequestMapping("spring/v5")
public class UserController {
    @Resource
    private UserService userService;


    @ApiOperation(value = "用户登陆接口")
    @PostMapping("user")
    public ResultView userLogin(@ApiParam(value = "参数</br>1.username:用户名（必填）</br>2.password:密码 (必填)</br>") @RequestBody HashMap<String,Object> paramsMap) {
        log.info("userLogin params {}", JSON.toJSONString(paramsMap));
        JSONObject paramsJson = new JSONObject(paramsMap);
        User user = userService.selectUserByUserNameAndPassword(paramsJson);
        if (Optional.ofNullable(user).isPresent()){
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }
        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "添加用户接口")
    @PutMapping("user")
    public ResultView addUser(@ApiParam(name = "User实体类",value = "User") @RequestBody User user) {
        log.info("addUser params {}", JSON.toJSONString(user));
        boolean result = userService.addUser(user);
        if (result){
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }
        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "转账接口")
    @PatchMapping("user")
    public ResultView moveMoney(@ApiParam(name = "Map对象",value = "参数</br>1.moveOutId:转出用户ID（必填）</br>2.moveOutMoney:转出数额(必填)</br>3.moveInId:转入账户(必填)</br>") @RequestBody HashMap<String,Object> paramsMap) {
        log.info("addUser params {}", JSON.toJSONString(paramsMap));
        JSONObject jsonParams = new JSONObject(paramsMap);
        boolean result = userService.moveMoney(jsonParams);
        if (result){
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }
        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "用户列表接口")
    @PostMapping("user/list")
    public ResultView<User> userList(@ApiParam(name = "Map对象",value = "参数</br>1.page:页码（选填-默认1）</br>2.pageSize:一页数据量(选填-默认5)</br>3.order:排序字段(选填默认创建时间，1-创建时间排序)</br>4.sort:序列(选填默认降序，1-降序，2-升序)</br>5.name:姓名(默认全称匹配）</br>6.isLike:匹配模式(默认全称匹配，1-全称，2-模糊）</br>") @RequestBody HashMap<String,Object> paramsMap) {
        log.info("userList params {}",JSON.toJSONString(paramsMap));
        JSONObject jsonParams = new JSONObject(paramsMap);
        PageInfo<User> userPageInfo = userService.userList(jsonParams);
        if (Optional.ofNullable(userPageInfo).isPresent()){
            ResultView resultView = new ResultView<User>();
            resultView.setMsgCode(ResultCode.SUCCESS);
            resultView.setPageNum(userPageInfo.getPageNum());
            resultView.setPageSize(userPageInfo.getPageSize());
            resultView.setTotal(userPageInfo.getTotal());
            resultView.setMaxPage(userPageInfo.getPages());
            resultView.setData(userPageInfo.getList());
            return resultView;
        }
        return null;
    }


    @ApiOperation(value = "用户详情接口")
    @GetMapping("user/{id}")
    public ResultView userDetail(@ApiParam(name = "用户id",value = "参数</br>1.id:用户id") @PathVariable Integer id) {
        log.info("userDetail params {}",JSON.toJSONString(id));
        User user = userService.userDetail(id);
        if (Optional.ofNullable(user).isPresent()){
            ResultView resultView = new ResultView();
            resultView.setMsgCode(ResultCode.SUCCESS);
            resultView.setData(user);
            return resultView;
        }
        return null;
    }
}
