package cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule;

import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.rule.vo.*;
import cn.iocoder.yudao.adminserver.modules.ruleManage.controller.ruleFactor.vo.RuleFactorExportReqVO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.convert.rule.RuleConvert;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.rule.RuleDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.dal.dataobject.ruleFactor.RuleFactorDO;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.rule.RuleService;
import cn.iocoder.yudao.adminserver.modules.ruleManage.service.ruleFactor.RuleFactorService;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@WebServlet(urlPatterns = "/ruleCalculate")
public class RuleCalculateServlet extends HttpServlet {

    @Resource
    private RuleService ruleService;
    @Resource
    private RuleFactorService factorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doGet----------------");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doPost----------------");
        String json = req.getParameter("json");
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (null != jsonObject) {
            String ruletype = (String) jsonObject.get("ruletype");
            RuleExportReqVO ruleVO = new RuleExportReqVO();
            ruleVO.setRuletype(ruletype);
            List<RuleDO> ruleDOS = ruleService.getList(ruleVO); //根据规则类型获取所有规则
            String returnMsg = "OK"; //返回信息
            for (RuleDO ruleDO : ruleDOS) {
                RuleFactorExportReqVO ruleFactorVO = new RuleFactorExportReqVO();
                ruleFactorVO.setRuleno(ruleDO.getRuleno());
                List<RuleFactorDO> ruleFactorDOS = factorService.getList(ruleFactorVO); //根据规则号获取下边的所有因子
                boolean flag = false;
                //比较因子
                for (RuleFactorDO ruleFactor : ruleFactorDOS) {
                    String value = (String) jsonObject.get(ruleFactor.getFactorcode());//接口要比较的值
                    String judgetype = ruleFactor.getJudgetype();
                    String factorvaluecode = ruleFactor.getFactorvaluecode();//规则因子值
                    if (null != value) {
                          switch (judgetype) {
                            case "EQUAL":  //相等
                                 flag = value.trim().equals(factorvaluecode.trim());
                                break;
                            case "EXCLUDE": //排除
                                flag = !value.trim().equals(factorvaluecode.trim());
                                break;
                            case "NULLVALUE"://空值
                                flag =   null==value;
                                break;
                            case "MORETHEN"://大于
                                flag =  new BigDecimal( value).compareTo(new BigDecimal(factorvaluecode))==1;//a>b, 返回1
                                break;
                            case "LESSTHEN"://小于
                                flag =  new BigDecimal( value).compareTo(new BigDecimal(factorvaluecode))==-1;// a<b, 返回-1
                                break;
                            default:
                                break;
                        }
                    }
                    if (flag) {
                        returnMsg = "错误信息为{：" + ruleFactor.getFactorname() + "计算逻辑为：" + judgetype + "和接口中" + factorvaluecode + "的值不匹配，因此规则返回:" + ruleDO.getRuleresult();
                    }
                }
                if (flag) {
                    return;
                }
            }
            resp.getOutputStream().print(returnMsg);
        }else{
            //resp.getOutputStream().print("请求参数异常");
        }

    }

    @Override
    public void init() throws ServletException {
        System.out.println("-----------init----------------");
        super.init();
    }

}
