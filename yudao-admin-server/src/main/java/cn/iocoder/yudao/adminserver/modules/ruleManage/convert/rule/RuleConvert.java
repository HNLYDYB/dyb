package cn.iocoder.yudao.adminserver.modules.ruleManage.convert.rule;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleExcelVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleRespVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 规则管理 Convert
 *
 * @author dyb
 */
@Mapper
public interface RuleConvert {

    RuleConvert INSTANCE = Mappers.getMapper(RuleConvert.class);

    RuleDO convert(RuleCreateReqVO bean);

    RuleDO convert(RuleUpdateReqVO bean);

    RuleRespVO convert(RuleDO bean);

    List<RuleRespVO> convertList(List<RuleDO> list);

    PageResult<RuleRespVO> convertPage(PageResult<RuleDO> page);

    List<RuleExcelVO> convertList02(List<RuleDO> list);

}
