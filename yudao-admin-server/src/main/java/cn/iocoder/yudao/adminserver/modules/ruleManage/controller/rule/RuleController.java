package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.rule.RuleConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.rule.RuleService;
import cn.iocoder.yudao.adminserver.modules.system.controller.auth.vo.auth.SysAuthLoginReqVO;
import cn.iocoder.yudao.adminserver.modules.system.controller.auth.vo.auth.SysAuthLoginRespVO;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getUserAgent;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "规则管理")
@RestController
@RequestMapping("/ruleManage/rule")
@Validated
public class RuleController {

    @Resource
    private RuleService ruleService;

    @PostMapping("/createRule")
    @ApiOperation("创建规则管理")
    @PreAuthorize("@ss.hasPermission('ruleManage::create')")
    public CommonResult<Long> createRule(@Valid @RequestBody RuleCreateReqVO createReqVO) {
        return success(ruleService.createRule(createReqVO));
    }

    @PutMapping("/updateRule")
    @ApiOperation("更新规则管理")
    @PreAuthorize("@ss.hasPermission('ruleManage::update')")
    public CommonResult<Boolean> updateRule(@Valid @RequestBody RuleUpdateReqVO updateReqVO) {
        ruleService.updateRule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/deleteRule")
    @ApiOperation("删除规则管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ruleManage::delete')")
    public CommonResult<Boolean> deleteRule(@RequestParam("id") Long id) {
        ruleService.deleteRule(id);
        return success(true);
    }

    @GetMapping("/getRule")
    @ApiOperation("获得规则管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('ruleManage::query')")
    public CommonResult<RuleRespVO> getRule(@RequestParam("id") Long id) {
        RuleDO rule= ruleService.getRule(id);
        return success(RuleConvert.INSTANCE.convert(rule));
    }
    @GetMapping("/getRulebyNo")
    @ApiOperation("获得规则管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('ruleManage::query')")
    public CommonResult<RuleRespVO> getRulebyNo(@RequestParam("ruleno") String ruleno) {
        RuleDO rule= ruleService.getRulebyNo(ruleno);
        return success(RuleConvert.INSTANCE.convert(rule));
    }

    @GetMapping("/list")
    @ApiOperation("获得规则管理列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('ruleManage::query')")
    public CommonResult<List<RuleRespVO>> getList(@RequestParam("ids") Collection<Long> ids) {
        List<RuleDO> list = ruleService.getList(ids);
        return success(RuleConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得规则管理分页")
    @PreAuthorize("@ss.hasPermission('ruleManage::query')")
    public CommonResult<PageResult<RuleRespVO>> getPage(@Valid RulePageReqVO pageVO) {
        PageResult<RuleDO> pageResult = ruleService.getPage(pageVO);
        return success(RuleConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出规则管理 Excel")
    @PreAuthorize("@ss.hasPermission('ruleManage::export')")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid RuleExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RuleDO> list = ruleService.getList(exportReqVO);
        // 导出 Excel
        List<RuleExcelVO> datas = RuleConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "规则管理.xls", "数据", RuleExcelVO.class, datas);
    }


    @GetMapping("/getRuleNo")
    @ApiOperation("生成规则号")
    public CommonResult<RuleRespVO> getRuleNo() {
        UUID ruleno = UUID.randomUUID();
        RuleRespVO ruleRespVO = new RuleRespVO();
        ruleRespVO.setRuleno(ruleno.toString().replaceAll("\\-","") );
        // 返回结果
        return success(ruleRespVO);
    }


}
