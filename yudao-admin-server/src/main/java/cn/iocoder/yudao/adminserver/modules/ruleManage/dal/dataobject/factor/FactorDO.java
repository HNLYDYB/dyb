package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor;

import cn.iocoder.yudao.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 因子配置 DO
 *
 * @author 芋艿
 */
@TableName("r_factor")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 因子代码
     */
    private String factorcode;
    /**
     * 因子名称
     */
    private String factorname;
    /**
     * 因子类型
     */
    private String factortype;
    /**
     * 命中逻辑
     */
    private String  judgetype;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;

}
