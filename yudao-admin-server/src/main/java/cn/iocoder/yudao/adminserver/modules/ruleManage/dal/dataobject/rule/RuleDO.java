package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 规则管理 DO
 *
 * @author dyb
 */
@TableName("r_rule")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 规则号
     */
    private String ruleno;
    /**
     * 规则名称
     */
    private String rulename;
    /**
     * 规则类型
     */
    private String ruletype;

    private String ruleresult;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 起始时间
     */
    private Date startdate;
    /**
     * 结束时间
     */
    private Date enddate;

    /**
     * 状态
     */
    private Integer status;


}
