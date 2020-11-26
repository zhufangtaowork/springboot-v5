package com.example.springbootv5.pojo;

import com.example.springbootv5.until.ExcelAnno;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName： ExcelUser
 * @Description： TODO
 * @Date： 2020/10/22 4:03 下午
 * @author： ZhuFangTao
 */
@Data
public class ExcelUser {
    @ExcelAnno(head = "序号")
    private Integer num;
    @ExcelAnno(head = "帐号")
    private String account;
    @ExcelAnno(head = "所属城市")
    private String city;
    @ExcelAnno(head = "登陆次数")
    private Integer loginNum;
    @ExcelAnno(head = "最后一次登陆时间")
    private Date loginTime;
    @ExcelAnno(head = "最后一次登陆IP")
    private String ip;
    @ExcelAnno(head = "最后一次登陆地点")
    private String address;
    @ExcelAnno(head = "帐号开设时间")
    private Date openTime;
}
