package com.example.springbootv5.pojo;

import com.example.springbootv5.until.ExcelAnno;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目名称：springboot_demo
 * 类 名 称：User
 * 类 描 述：TODO
 * 创建时间：2020/3/11 4:53 下午
 * 创 建 人：ZhuFangTao
 * @author fangtaozhu
 */
@Data
@ApiModel("User")
@Alias("User")
public class User {

    @ApiModelProperty(name = "用户ID",value = "id")
    private Integer id;
    @ApiModelProperty(name = "用户姓名",value = "name")
    private String name;
    @ApiModelProperty(name = "用户密码",value = "password")
    private String password;
    @ApiModelProperty(name = "创建时间",value = "createTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(name = "更新时间",value = "updateTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @ApiModelProperty(name = "用户状态1有效，0无效",value = "status")
    private Integer status;
    @ApiModelProperty(name = "用户账户",value = "money")
    private BigDecimal money;
}
