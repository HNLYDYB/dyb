package cn.iocoder.yudao.adminserver.modules.ruleManage.convert.factor;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor.FactorDO;

/**
 * 因子配置 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface FactorConvert {

    FactorConvert INSTANCE = Mappers.getMapper(FactorConvert.class);

    FactorDO convert(FactorCreateReqVO bean);

    FactorDO convert(FactorUpdateReqVO bean);

    FactorRespVO convert(FactorDO bean);

    List<FactorRespVO> convertList(List<FactorDO> list);


    PageResult<FactorRespVO> convertPage(PageResult<FactorDO> page);

    List<FactorExcelVO> convertList02(List<FactorDO> list);

}
