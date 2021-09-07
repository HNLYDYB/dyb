package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 规则管理 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class RuleBaseVO {

    @ApiModelProperty(value = "规则号")
    private String ruleno;

    @ApiModelProperty(value = "规则名称")
    private String rulename;

    @ApiModelProperty(value = "规则类型")
    private String ruletype;

    @ApiModelProperty(value = "规则返回结果")
    private String ruleresult;

    @ApiModelProperty(value = "优先级")
    private String priority;

    @ApiModelProperty(value = "起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date startdate;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date enddate;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
