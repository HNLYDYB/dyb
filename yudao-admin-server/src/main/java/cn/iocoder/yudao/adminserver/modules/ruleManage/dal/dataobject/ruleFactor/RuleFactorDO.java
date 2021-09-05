package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 规则使用因子 DO
 *
 * @author dyb
 */
@TableName("r_rule_factor")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleFactorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 规则主键
     */
    private Long ruleid;
    /**
     * 规则号
     */
    private String ruleno;

    /**
     * 因子类型
     */
    private String factortype;
    /**
     * 因子代码
     */
    private String factorcode;
    /**
     * 因子名称
     */
    private String factorname;
    /**
     * 因子逻辑
     */
    private String judgetype;
    /**
     * 命中值代码t
     */
    private String factorvaluecode;
    /**
     * 命中值逻辑
     */
    private String factorvaluename;
    /**
     * 状态
     */
    private Integer status;

}
