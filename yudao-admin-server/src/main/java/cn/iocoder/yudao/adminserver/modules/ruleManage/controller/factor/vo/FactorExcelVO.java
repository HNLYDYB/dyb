package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 因子配置 Excel VO
 *
 * @author 芋艿
 */
@Data
public class FactorExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("因子代码")
    private String factorcode;

    @ExcelProperty("因子名称")
    private String factorname;

    @ExcelProperty("因子类型")
    private String factortype;

    @ExcelProperty("命中逻辑")
    private String judgetype;

    @ExcelProperty("显示顺序")
    private Integer sort;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
