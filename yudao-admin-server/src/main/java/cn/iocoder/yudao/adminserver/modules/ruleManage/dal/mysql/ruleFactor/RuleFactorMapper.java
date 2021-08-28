package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.ruleFactor;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorPageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则使用因子 Mapper
 *
 * @author dyb
 */
@Mapper
public interface RuleFactorMapper extends BaseMapperX<RuleFactorDO> {

    default PageResult<RuleFactorDO> selectPage(RuleFactorPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<RuleFactorDO>()
                .eqIfPresent("ruleid", reqVO.getRuleid())
                .eqIfPresent("ruleno", reqVO.getRuleno())
                .eqIfPresent("ruletype", reqVO.getRuletype())
                .eqIfPresent("factcode", reqVO.getFactcode())
                .likeIfPresent("factname", reqVO.getFactname())
                .eqIfPresent("judgetype", reqVO.getJudgetype())
                .eqIfPresent("factvaluecode", reqVO.getFactvaluecode())
                .likeIfPresent("factvaluename", reqVO.getFactvaluename())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }

    default List<RuleFactorDO> selectList(RuleFactorExportReqVO reqVO) {
        return selectList(new QueryWrapperX<RuleFactorDO>()
                .eqIfPresent("ruleid", reqVO.getRuleid())
                .eqIfPresent("ruleno", reqVO.getRuleno())
                .eqIfPresent("ruletype", reqVO.getRuletype())
                .eqIfPresent("factcode", reqVO.getFactcode())
                .likeIfPresent("factname", reqVO.getFactname())
                .eqIfPresent("judgetype", reqVO.getJudgetype())
                .eqIfPresent("factvaluecode", reqVO.getFactvaluecode())
                .likeIfPresent("factvaluename", reqVO.getFactvaluename())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }

}
