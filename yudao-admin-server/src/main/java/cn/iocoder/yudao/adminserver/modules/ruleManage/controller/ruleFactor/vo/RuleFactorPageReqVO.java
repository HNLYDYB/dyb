package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("规则使用因子分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleFactorPageReqVO extends PageParam {

    @ApiModelProperty(value = "规则主键")
    private Long ruleid;

    @ApiModelProperty(value = "规则号")
    private String ruleno;

    @ApiModelProperty(value = "规则类型")
    private String ruletype;

    @ApiModelProperty(value = "因子代码")
    private String factcode;

    @ApiModelProperty(value = "因子名称")
    private String factname;

    @ApiModelProperty(value = "因子逻辑")
    private String judgetype;

    @ApiModelProperty(value = "命中值代码")
    private String factvaluecode;

    @ApiModelProperty(value = "命中值逻辑")
    private String factvaluename;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
