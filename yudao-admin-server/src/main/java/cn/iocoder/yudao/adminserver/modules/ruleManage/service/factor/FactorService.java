package cn.iocoder.yudao.adminserver.modules.ruleManage.service.factor;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor.FactorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 因子配置 Service 接口
 *
 * @author 芋艿
 */
public interface FactorService {

    /**
     * 创建因子配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid FactorCreateReqVO createReqVO);

    /**
     * 更新因子配置
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid FactorUpdateReqVO updateReqVO);

    /**
     * 删除因子配置
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得因子配置
     *
     * @param id 编号
     * @return 因子配置
     */
    FactorDO get(Long id);

    /**
     * 获得因子配置列表
     *
     * @param ids 编号
     * @return 因子配置列表
     */
    List<FactorDO> getList(Collection<Long> ids);

    /**
     * 获得因子配置分页
     *
     * @param pageReqVO 分页查询
     * @return 因子配置分页
     */
    PageResult<FactorDO> getPage(FactorPageReqVO pageReqVO);

    /**
     * 获得因子配置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 因子配置列表
     */
    List<FactorDO> getList(FactorExportReqVO exportReqVO);

    List<FactorDO> getListByMap(Map map);

}
