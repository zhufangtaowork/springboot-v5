package com.example.springbootv5.controller;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootv5.common.ResultCode;
import com.example.springbootv5.common.ResultView;
import com.example.springbootv5.pojo.ExcelUser;
import com.example.springbootv5.pojo.User;
import com.example.springbootv5.service.UserService;
import com.example.springbootv5.until.Excel;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    public ResultView userLogin(@ApiParam(value = "参数</br>1.username:用户名（必填）</br>2.password:密码 (必填)</br>") @RequestBody HashMap<String, Object> paramsMap) {
        log.info("userLogin params {}", JSON.toJSONString(paramsMap));
        JSONObject paramsJson = new JSONObject(paramsMap);
        User user = userService.selectUserByUserNameAndPassword(paramsJson);
        if (Optional.ofNullable(user).isPresent()) {
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }
        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "添加用户接口")
    @PutMapping("user")
    public ResultView addUser(@ApiParam(name = "User实体类", value = "User") @RequestBody User user) {
        log.info("addUser params {}", JSON.toJSONString(user));
        boolean result = userService.addUser(user);
        if (result) {
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }
        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "转账接口")
    @PatchMapping("user")
    public ResultView moveMoney(@ApiParam(name = "Map对象", value = "参数</br>1.moveOutId:转出用户ID（必填）</br>2.moveOutMoney:转出数额(必填)</br>3.moveInId:转入账户(必填)</br>") @RequestBody HashMap<String, Object> paramsMap) {
        log.info("addUser params {}", JSON.toJSONString(paramsMap));
        JSONObject jsonParams = new JSONObject(paramsMap);
        boolean result = userService.moveMoney(jsonParams);
        if (result) {
            return new ResultView().setMsgCode(ResultCode.SUCCESS);
        }

        return new ResultView().setMsgCode(ResultCode.FAIL);
    }

    @ApiOperation(value = "用户列表接口")
    @PostMapping("user/list")
    public ResultView<User> userList(@ApiParam(name = "Map对象", value = "参数</br>1.page:页码（选填-默认1）</br>2.pageSize:一页数据量(选填-默认5)</br>3.order:排序字段(选填默认创建时间，1-创建时间排序)</br>4.sort:序列(选填默认降序，1-降序，2-升序)</br>5.name:姓名(默认全称匹配）</br>6.isLike:匹配模式(默认全称匹配，1-全称，2-模糊）</br>") @RequestBody HashMap<String, Object> paramsMap) {
        log.info("userList params {}", JSON.toJSONString(paramsMap));
        JSONObject jsonParams = new JSONObject(paramsMap);
        PageInfo<User> userPageInfo = userService.userList(jsonParams);
        if (Optional.ofNullable(userPageInfo).isPresent()) {
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
    @GetMapping(value = "user/{id}", headers = "Accept=application/json")
    public ResultView<User> userDetail(@ApiParam(name = "用户id", required = true) @PathVariable(value = "id") Integer id) {
        log.info("userDetail params {}", JSON.toJSONString(id));
        User user = userService.userDetail(id);
        if (Optional.ofNullable(user).isPresent()) {
            ResultView<User> resultView = new ResultView<>();
            resultView.setMsgCode(ResultCode.SUCCESS);
            resultView.setData(user);
            return resultView;
        }
        return null;
    }



//    @ApiOperation(value = "用户详情接口")
//    @GetMapping(value = "exportExcel", headers = "Accept=application/json")
//    public void export(HttpServletRequest request, HttpServletResponse response) {
//        User user = new User();
//        user.setName("小明");
//        user.setPassword("123");
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
//        user.setStatus(0);
//        user.setMoney(new BigDecimal("24"));
//        User user1 = new User();
//        user1.setName("小刚");
//        user1.setPassword("1234");
//        user1.setCreateTime(new Date());
//        user1.setUpdateTime(new Date());
//        user1.setStatus(1);
//        user1.setMoney(new BigDecimal("24"));
//
//        //获取数据
//        List<User> list = new ArrayList<>();
//        list.add(user);
//        list.add(user1);
//
//        //excel标题
//        String[] title = {"用户姓名", "用户密码", "创建时间", "更新时间","用户状态","用户账户"};
//
//        //excel文件名
//        String fileName = "用户信息表.xls";
//
//        //sheet名
//        String sheetName = "用户信息表";
//        String [][] content = new String[list.size()][5];
//
//        for (int i = 0; i < list.size(); i++) {
//            content[i] = new String[title.length];
//            User obj = list.get(i);
//            content[i][0] = obj.getName();
//            content[i][1] = obj.getPassword();
//            content[i][2] = String.valueOf(obj.getCreateTime());
//            content[i][3] = String.valueOf(obj.getUpdateTime());
//            content[i][4] = String.valueOf(obj.getStatus());
//            content[i][5] = String.valueOf(obj.getMoney());
//
//        }
//        //创建HSSFWorkbook
//        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, content, null);
//
//        //响应到客户端
//        try {
//            this.setResponseHeader(response, fileName);
//            OutputStream os = response.getOutputStream();
//            wb.write(os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//
//
//
//    @ApiOperation(value = "用户详情接口")
//    @GetMapping(value = "excel", headers = "Accept=application/json")
//    public void exportNew(HttpServletRequest request, HttpServletResponse response,OutputStream out) {
//        ExcelUser user = new ExcelUser();
//        user.setName("小明");
//        user.setPassword("123");
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
//        user.setStatus(0);
//        user.setMoney(new BigDecimal("24"));
//        ExcelUser user1 = new ExcelUser();
//        user1.setName("小刚");
//        user1.setPassword("1234");
//        user1.setCreateTime(new Date());
//        user1.setUpdateTime(new Date());
//        user1.setStatus(1);
//        user1.setMoney(new BigDecimal("24"));
//
//        //获取数据
//        List<ExcelUser> list = new ArrayList<>();
//        list.add(user);
//        list.add(user1);
//        ExportExcelUtil<ExcelUser> userExportExcelUtil = new ExportExcelUtil<>();
//
//        try {
//            userExportExcelUtil.exportExcel("用户表",list,out,"yyyy-MM-dd HH:mm:ss",response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @ApiOperation(value = "用户详情接口")
    @GetMapping(value = "newExcel", headers = "Accept=application/json")
    public void exportUser(HttpServletRequest request, HttpServletResponse response) {
        ExcelUser user = new ExcelUser();
        user.setNum(1);
        user.setAccount("石涛");
        user.setCity("北京");
        user.setLoginNum(3);
        user.setLoginTime(new Date());
        user.setIp("168.0.0.1");
        user.setAddress("北京");
        user.setOpenTime(new Date());


        ExcelUser user1 = new ExcelUser();
        user1.setNum(2);
        user1.setAccount("双龙");
        user1.setCity("北京-乌鲁木齐-新疆");
        user1.setLoginNum(3);
        user1.setLoginTime(new Date());
        user1.setIp("168.0.0.1");
        user1.setAddress("北京");
        user1.setOpenTime(new Date());;

        //获取数据
        List<ExcelUser> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        Excel<ExcelUser> excelUserExcel = new Excel<>();

        try {
            HSSFWorkbook workbook = excelUserExcel.getHSSFWorkbook("用户表", list, "yyyy-MM-dd HH:mm:ss");
            this.setResponseHeader(response,"用户信息表.xls");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
