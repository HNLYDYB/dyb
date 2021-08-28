package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("规则使用因子更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleFactorUpdateReqVO extends RuleFactorBaseVO {

    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "主键不能为空")
    private Long id;

}
