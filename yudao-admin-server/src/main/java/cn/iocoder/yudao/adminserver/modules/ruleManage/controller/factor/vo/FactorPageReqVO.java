package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("因子配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FactorPageReqVO extends PageParam {

    @ApiModelProperty(value = "因子代码")
    private String factorcode;

    @ApiModelProperty(value = "因子名称")
    private String factorname;

    @ApiModelProperty(value = "因子类型")
    private String factortype;

    @ApiModelProperty(value = "命中逻辑")
    private String judgetype;

    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
