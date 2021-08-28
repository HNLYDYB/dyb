package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "规则管理 Excel 导出 Request VO", description = "参数和 RulePageReqVO 是一致的")
@Data
public class RuleExportReqVO {


    @ApiModelProperty(value = "规则号")
    private String ruleno;

    @ApiModelProperty(value = "规则名称")
    private String rulename;

    @ApiModelProperty(value = "规则类型")
    private String ruletype;

    @ApiModelProperty(value = "优先级")
    private String priority;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始起始时间")
    private Date beginStartdate;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束起始时间")
    private Date endStartdate;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始结束时间")
    private Date beginEnddate;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束结束时间")
    private Date endEnddate;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
