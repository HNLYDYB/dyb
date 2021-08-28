package cn.iocoder.yudao.adminserver.modules.ruleManage.service.ruleFactor.impl;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorPageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.ruleFactor.RuleFactorConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.ruleFactor.RuleFactorMapper;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.ruleFactor.RuleFactorService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;



/**
 * 规则使用因子 Service 实现类
 *
 * @author dyb
 */
@Service
@Validated
public class RuleFactorServiceImpl implements RuleFactorService {

    @Resource
    private RuleFactorMapper Mapper;

    @Override
    public Long create(RuleFactorCreateReqVO createReqVO) {
        // 插入
        RuleFactorDO ruleFactorDO = RuleFactorConvert.INSTANCE.convert(createReqVO);
        Mapper.insert(ruleFactorDO);
        // 返回
        return ruleFactorDO.getId();
    }

    @Override
    public void update(RuleFactorUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        RuleFactorDO updateObj = RuleFactorConvert.INSTANCE.convert(updateReqVO);
        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (Mapper.selectById(id) == null) {
           // throw exception(_NOT_EXISTS);
        }
    }

    @Override
    public RuleFactorDO get(Long id) {
        return Mapper.selectById(id);
    }

    @Override
    public List<RuleFactorDO> getList(Collection<Long> ids) {
        return Mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RuleFactorDO> getPage(RuleFactorPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<RuleFactorDO> getList(RuleFactorExportReqVO exportReqVO) {
        return Mapper.selectList(exportReqVO);
    }

}
