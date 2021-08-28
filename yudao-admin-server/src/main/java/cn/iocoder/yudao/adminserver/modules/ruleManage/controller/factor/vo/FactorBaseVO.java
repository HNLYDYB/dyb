package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 因子配置 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FactorBaseVO {

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

}
