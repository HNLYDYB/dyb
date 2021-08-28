package cn.iocoder.yudao.adminserver.modules.ruleManage.service.factor.impl;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.FactorCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.FactorExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.FactorPageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.FactorUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.factor.FactorConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor.FactorDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.factor.FactorMapper;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.factor.FactorService;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * 因子配置 Service 实现类
 *
 * @author 芋艿
 */
@Service
@Validated
public class FactorServiceImpl implements FactorService {

    @Resource
    private FactorMapper factorMapper;

    @Override
    public Long create(FactorCreateReqVO createReqVO) {
        // 插入
        FactorDO  factorDO= FactorConvert.INSTANCE.convert(createReqVO);
        factorMapper.insert(factorDO);
        // 返回
        return factorDO.getId();
    }

    @Override
    public void update(FactorUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        FactorDO updateObj = FactorConvert.INSTANCE.convert(updateReqVO);
        factorMapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        factorMapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (factorMapper.selectById(id) == null) {
            //throw ServiceExceptionUtil.exception(RULE_NOT_EXISTS);
        }
    }

    @Override
    public FactorDO get(Long id) {
        return factorMapper.selectById(id);
    }

    @Override
    public List<FactorDO> getList(Collection<Long> ids) {
        return factorMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FactorDO> getPage(FactorPageReqVO pageReqVO) {
        return factorMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FactorDO> getList(FactorExportReqVO exportReqVO) {
        return factorMapper.selectList(exportReqVO);
    }



}
