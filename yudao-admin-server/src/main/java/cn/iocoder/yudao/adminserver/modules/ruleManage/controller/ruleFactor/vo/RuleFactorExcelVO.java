package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 规则使用因子 Excel VO
 *
 * @author dyb
 */
@Data
public class RuleFactorExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("规则主键")
    private Long ruleid;

    @ExcelProperty("规则号")
    private String ruleno;

    @ExcelProperty("规则类型")
    private String ruletype;

    @ExcelProperty("因子代码")
    private String factorcode;

    @ExcelProperty("因子名称")
    private String factorname;

    @ExcelProperty("因子逻辑")
    private String judgetype;

    @ExcelProperty("命中值代码")
    private String factorvaluecode;

    @ExcelProperty("命中值逻辑")
    private String factorvaluename;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
