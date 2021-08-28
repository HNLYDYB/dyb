package cn.iocoder.yudao.adminserver.modules.ruleManage.convert.ruleFactor;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorExcelVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorRespVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 规则使用因子 Convert
 *
 * @author dyb
 */
@Mapper
public interface RuleFactorConvert {

    RuleFactorConvert INSTANCE = Mappers.getMapper(RuleFactorConvert.class);

    RuleFactorDO convert(RuleFactorCreateReqVO bean);

    RuleFactorDO convert(RuleFactorUpdateReqVO bean);

    RuleFactorRespVO convert(RuleFactorDO bean);

    List<RuleFactorRespVO> convertList(List<RuleFactorDO> list);

    PageResult<RuleFactorRespVO> convertPage(PageResult<RuleFactorDO> page);

    List<RuleFactorExcelVO> convertList02(List<RuleFactorDO> list);

}
