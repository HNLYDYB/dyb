package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.factor;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor.FactorDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.*;

/**
 * 因子配置 Mapper
 *
 * @author 芋艿
 */
@Mapper
public interface FactorMapper extends BaseMapperX<FactorDO> {

    default PageResult<FactorDO> selectPage(FactorPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<FactorDO>()
                .eqIfPresent("factorcode", reqVO.getFactorcode())
                .likeIfPresent("factorname", reqVO.getFactorname())
                .eqIfPresent("factortype", reqVO.getFactortype())
                .eqIfPresent("judgetype", reqVO.getJudgetype())
                .eqIfPresent("sort", reqVO.getSort())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }

    default List<FactorDO> selectList(FactorExportReqVO reqVO) {
        return selectList(new QueryWrapperX<FactorDO>()
                .eqIfPresent("factorcode", reqVO.getFactorcode())
                .likeIfPresent("factorname", reqVO.getFactorname())
                .eqIfPresent("factortype", reqVO.getFactortype())
                .eqIfPresent("judgetype", reqVO.getJudgetype())
                .eqIfPresent("sort", reqVO.getSort())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }



}
