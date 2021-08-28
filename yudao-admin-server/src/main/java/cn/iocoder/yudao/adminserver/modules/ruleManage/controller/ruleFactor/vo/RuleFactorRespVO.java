package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("规则使用因子 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleFactorRespVO extends RuleFactorBaseVO {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
