package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 规则管理 Excel VO
 *
 * @author dyb
 */
@Data
public class RuleExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("规则号")
    private String ruleno;

    @ExcelProperty("规则名称")
    private String rulename;

    @ExcelProperty("规则类型")
    private String ruletype;

    @ExcelProperty("优先级")
    private String priority;

    @ExcelProperty("起始时间")
    private Date startdate;

    @ExcelProperty("结束时间")
    private Date enddate;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;



}
