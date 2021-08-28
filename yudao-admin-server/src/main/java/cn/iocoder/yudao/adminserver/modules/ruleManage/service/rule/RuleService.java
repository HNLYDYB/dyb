package cn.iocoder.yudao.adminserver.modules.ruleManage.service.rule;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RulePageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 规则管理 Service 接口
 *
 * @author dyb
 */
public interface RuleService {

    /**
     * 创建规则管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRule(@Valid RuleCreateReqVO createReqVO);

    /**
     * 更新规则管理
     *
     * @param updateReqVO 更新信息
     */
    void updateRule(@Valid RuleUpdateReqVO updateReqVO);

    /**
     * 删除规则管理
     *
     * @param id 编号
     */
    void deleteRule(Long id);

    /**
     * 获得规则管理
     *
     * @param id 编号
     * @return 规则管理
     */
    RuleDO getRule(Long id);

    /**
     * 获得规则管理列表
     *
     * @param ids 编号
     * @return 规则管理列表
     */
    List<RuleDO> getList(Collection<Long> ids);

    /**
     * 获得规则管理分页
     *
     * @param pageReqVO 分页查询
     * @return 规则管理分页
     */
    PageResult<RuleDO> getPage(RulePageReqVO pageReqVO);

    /**
     * 获得规则管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 规则管理列表
     */
    List<RuleDO> getList(RuleExportReqVO exportReqVO);

}
