package cn.iocoder.yudao.adminserver.modules.ruleManage.dal.mysql.rule;

import java.util.*;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RuleExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.RulePageReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.adminserver.modules.system.controller.auth.vo.session.SysUserSessionPageReqVO;
import cn.iocoder.yudao.adminserver.modules.system.dal.dataobject.auth.SysUserSessionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;

import org.apache.ibatis.annotations.Mapper;


/**
 * 规则管理 Mapper
 *
 * @author dyb
 */
@Mapper
public interface RuleMapper extends BaseMapperX<RuleDO> {



    default PageResult<RuleDO> selectPage(RulePageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<RuleDO>()
                .eqIfPresent("ruleno", reqVO.getRuleno())
                .likeIfPresent("rulename", reqVO.getRulename())
                .eqIfPresent("ruletype", reqVO.getRuletype())
                .eqIfPresent("priority", reqVO.getPriority())
                .betweenIfPresent("startdate", reqVO.getBeginStartdate(), reqVO.getEndStartdate())
                .betweenIfPresent("enddate", reqVO.getBeginEnddate(), reqVO.getEndEnddate())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }

    default List<RuleDO> selectList(RuleExportReqVO reqVO) {
        return selectList(new QueryWrapperX<RuleDO>()
                .eqIfPresent("ruleno", reqVO.getRuleno())
                .likeIfPresent("rulename", reqVO.getRulename())
                .eqIfPresent("ruletype", reqVO.getRuletype())
                .eqIfPresent("priority", reqVO.getPriority())
                .betweenIfPresent("startdate", reqVO.getBeginStartdate(), reqVO.getEndStartdate())
                .betweenIfPresent("enddate", reqVO.getBeginEnddate(), reqVO.getEndEnddate())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id")        );
    }

}
