package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo;

import lombok.*;
import io.swagger.annotations.*;

import java.util.Date;

@ApiModel("规则管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RuleRespVO extends RuleBaseVO {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
