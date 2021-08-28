package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.ruleFactor.RuleFactorConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.ruleFactor.RuleFactorService;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "规则使用因子")
@RestController
@RequestMapping("/ruleManage/ruleFactor")
@Validated
public class RuleFactorController {

    @Resource
    private RuleFactorService Service;

    @PostMapping("/create")
    @ApiOperation("创建规则使用因子")
    @PreAuthorize("@ss.hasPermission('RuleFactor::create')")
    public CommonResult<Long> create(@Valid @RequestBody RuleFactorCreateReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新规则使用因子")
    @PreAuthorize("@ss.hasPermission('RuleFactor::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody RuleFactorUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除规则使用因子")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('RuleFactor::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得规则使用因子")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('RuleFactor::query')")
    public CommonResult<RuleFactorRespVO> get(@RequestParam("id") Long id) {
        RuleFactorDO ruleFactorDO  = Service.get(id);
        return success(RuleFactorConvert.INSTANCE.convert(ruleFactorDO));
    }

    @GetMapping("/list")
    @ApiOperation("获得规则使用因子列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('RuleFactor::query')")
    public CommonResult<List<RuleFactorRespVO>> getList(@RequestParam("ids") Collection<Long> ids) {
        List<RuleFactorDO> list = Service.getList(ids);
        return success(RuleFactorConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得规则使用因子分页")
    @PreAuthorize("@ss.hasPermission('RuleFactor::query')")
    public CommonResult<PageResult<RuleFactorRespVO>> getPage(@Valid RuleFactorPageReqVO pageVO) {
        PageResult<RuleFactorDO> pageResult = Service.getPage(pageVO);
        return success(RuleFactorConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出规则使用因子 Excel")
    @PreAuthorize("@ss.hasPermission('RuleFactor::export')")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid RuleFactorExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RuleFactorDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<RuleFactorExcelVO> datas = RuleFactorConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "规则使用因子.xls", "数据", RuleFactorExcelVO.class, datas);
    }

}
