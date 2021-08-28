package cn.iocoder.yudao.adminserver.modules.ruleManage.service.ruleFactor;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorPageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 规则使用因子 Service 接口
 *
 * @author dyb
 */
public interface RuleFactorService {

    /**
     * 创建规则使用因子
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid RuleFactorCreateReqVO createReqVO);

    /**
     * 更新规则使用因子
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid RuleFactorUpdateReqVO updateReqVO);

    /**
     * 删除规则使用因子
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得规则使用因子
     *
     * @param id 编号
     * @return 规则使用因子
     */
    RuleFactorDO get(Long id);

    /**
     * 获得规则使用因子列表
     *
     * @param ids 编号
     * @return 规则使用因子列表
     */
    List<RuleFactorDO> getList(Collection<Long> ids);

    /**
     * 获得规则使用因子分页
     *
     * @param pageReqVO 分页查询
     * @return 规则使用因子分页
     */
    PageResult<RuleFactorDO> getPage(RuleFactorPageReqVO pageReqVO);

    /**
     * 获得规则使用因子列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 规则使用因子列表
     */
    List<RuleFactorDO> getList(RuleFactorExportReqVO exportReqVO);

}
