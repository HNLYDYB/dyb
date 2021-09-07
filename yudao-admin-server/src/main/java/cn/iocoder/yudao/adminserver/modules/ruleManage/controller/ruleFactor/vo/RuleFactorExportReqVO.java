package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "规则使用因子 Excel 导出 Request VO", description = "参数和 RuleFactorPageReqVO 是一致的")
@Data
public class RuleFactorExportReqVO {

    @ApiModelProperty(value = "规则主键")
    private Long ruleid;

    @ApiModelProperty(value = "规则号")
    private String ruleno;

    @ApiModelProperty(value = "规则类型")
    private String ruletype;

    @ApiModelProperty(value = "因子代码")
    private String factorcode;

    @ApiModelProperty(value = "因子名称")
    private String factorname;

    @ApiModelProperty(value = "因子逻辑")
    private String judgetype;

    @ApiModelProperty(value = "命中值代码")
    private String factorvaluecode;

    @ApiModelProperty(value = "命中值逻辑")
    private String factorvaluename;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}