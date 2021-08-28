package cn.iocoder.yudao.adminserver.modules.ruleManage.service.rule.impl;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RulePageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.rule.RuleConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.rule.RuleMapper;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.rule.RuleService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 规则管理 Service 实现类
 *
 * @author dyb
 */
@Service
@Validated
public class RuleServiceImpl implements RuleService {

    @Resource
    private RuleMapper ruleMapper;

    @Override
    public Long createRule(RuleCreateReqVO createReqVO) {
        // 插入
        RuleDO rule = RuleConvert.INSTANCE.convert(createReqVO);
        ruleMapper.insert(rule);
        // 返回
        return rule.getId();
    }

    @Override
    public void updateRule(RuleUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        RuleDO updateObj = RuleConvert.INSTANCE.convert(updateReqVO);
        ruleMapper.updateById(updateObj);
    }

    @Override
    public void deleteRule(Long id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        ruleMapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (ruleMapper.selectById(id) == null) {
           // throw exception(_NOT_EXISTS);
        }
    }

    @Override
    public RuleDO getRule(Long id) {
        return ruleMapper.selectById(id);
    }

    @Override
    public List<RuleDO> getList(Collection<Long> ids) {
        return ruleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RuleDO> getPage(RulePageReqVO pageReqVO) {
        return ruleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RuleDO> getList(RuleExportReqVO exportReqVO) {
        return ruleMapper.selectList(exportReqVO);
    }

}
