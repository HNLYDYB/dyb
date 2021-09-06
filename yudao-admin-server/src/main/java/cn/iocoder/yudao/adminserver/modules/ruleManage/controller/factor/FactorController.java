package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.annotations.*;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.factor.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.factor.FactorDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.factor.FactorConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.factor.FactorService;

@Api(tags = "因子配置")
@RestController
@RequestMapping("/ruleManage/factor")
@Validated
public class FactorController {

    @Resource
    private FactorService Service;

    @PostMapping("/create")
    @ApiOperation("创建因子配置")
    @PreAuthorize("@ss.hasPermission('factor::create')")
    public CommonResult<Long> create(@Valid @RequestBody FactorCreateReqVO createReqVO) {
        String a=createReqVO.getJudgetype();
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新因子配置")
    @PreAuthorize("@ss.hasPermission('factor::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody FactorUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除因子配置")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('factor::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得因子配置")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('factor::query')")
    public CommonResult<FactorRespVO> get(@RequestParam("id") Long id) {
        FactorDO  factorDO= Service.get(id);
        return success(FactorConvert.INSTANCE.convert(factorDO));
    }

    @GetMapping("/list")
    @ApiOperation("获得因子配置列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('factor::query')")
    public CommonResult<List<FactorRespVO>> getList(@RequestParam("ids") Collection<Long> ids) {
        List<FactorDO> list = Service.getList(ids);
        return success(FactorConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得因子配置分页")
    @PreAuthorize("@ss.hasPermission('factor::query')")
    public CommonResult<PageResult<FactorRespVO>> getPage(@Valid FactorPageReqVO pageVO) {
        PageResult<FactorDO> pageResult = Service.getPage(pageVO);
        return success(FactorConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出因子配置 Excel")
    @PreAuthorize("@ss.hasPermission('factor::export')")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid FactorExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FactorDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<FactorExcelVO> datas = FactorConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "因子配置.xls", "数据", FactorExcelVO.class, datas);
    }


    @GetMapping("/getListByMap")
    @ApiOperation("获得因子配置列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('factor::query')")
    public CommonResult<List<FactorRespVO>> getListByMap(@Valid FactorRespVO  factorRespVO) {
        Map<String, Object> map = new HashMap();
        if (factorRespVO != null) {
            BeanMap beanMap = BeanMap.create(factorRespVO);
            for (Object key : beanMap.keySet()) {
              if(null!= beanMap.get(key)){
                  map.put(key + "", beanMap.get(key));
              }
            }
        }
        List<FactorDO> list = Service.getListByMap(map);
        return success(FactorConvert.INSTANCE.convertList(list));
    }
}
