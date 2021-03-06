package cn.iocoder.yudao.adminserver.modules.system.service.sms;

import cn.iocoder.yudao.adminserver.BaseDbUnitTest;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.sms.core.client.SmsClient;
import cn.iocoder.yudao.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.yudao.framework.sms.core.client.SmsCommonResult;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.adminserver.modules.system.controller.sms.vo.template.SysSmsTemplateCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.system.controller.sms.vo.template.SysSmsTemplateExportReqVO;
import cn.iocoder.yudao.adminserver.modules.system.controller.sms.vo.template.SysSmsTemplatePageReqVO;
import cn.iocoder.yudao.adminserver.modules.system.controller.sms.vo.template.SysSmsTemplateUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.system.dal.dataobject.sms.SysSmsChannelDO;
import cn.iocoder.yudao.adminserver.modules.system.dal.dataobject.sms.SysSmsTemplateDO;
import cn.iocoder.yudao.adminserver.modules.system.dal.mysql.sms.SysSmsTemplateMapper;
import cn.iocoder.yudao.adminserver.modules.system.enums.sms.SysSmsTemplateTypeEnum;
import cn.iocoder.yudao.adminserver.modules.system.mq.producer.sms.SysSmsProducer;
import cn.iocoder.yudao.adminserver.modules.system.service.sms.impl.SysSmsTemplateServiceImpl;
import cn.iocoder.yudao.framework.common.util.collection.ArrayUtils;
import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static cn.hutool.core.bean.BeanUtil.getFieldValue;
import static cn.hutool.core.util.RandomUtil.randomEle;
import static cn.iocoder.yudao.adminserver.modules.system.enums.SysErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.buildTime;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.max;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
* {@link SysSmsTemplateServiceImpl} ??????????????????
*
* @author ????????????
*/
@Import(SysSmsTemplateServiceImpl.class)
public class SysSmsTemplateServiceTest extends BaseDbUnitTest {

    @Resource
    private SysSmsTemplateServiceImpl smsTemplateService;

    @Resource
    private SysSmsTemplateMapper smsTemplateMapper;

    @MockBean
    private SysSmsChannelService smsChannelService;
    @MockBean
    private SmsClientFactory smsClientFactory;
    @MockBean
    private SmsClient smsClient;
    @MockBean
    private SysSmsProducer smsProducer;

    @Test
    @SuppressWarnings("unchecked")
    void testInitLocalCache() {
        // mock ??????
        SysSmsTemplateDO smsTemplate01 = randomSmsTemplateDO();
        smsTemplateMapper.insert(smsTemplate01);
        SysSmsTemplateDO smsTemplate02 = randomSmsTemplateDO();
        smsTemplateMapper.insert(smsTemplate02);

        // ??????
        smsTemplateService.initLocalCache();
        // ?????? deptCache ??????
        Map<String, SysSmsTemplateDO> smsTemplateCache = (Map<String, SysSmsTemplateDO>) getFieldValue(smsTemplateService, "smsTemplateCache");
        assertEquals(2, smsTemplateCache.size());
        assertPojoEquals(smsTemplate01, smsTemplateCache.get(smsTemplate01.getCode()));
        assertPojoEquals(smsTemplate02, smsTemplateCache.get(smsTemplate02.getCode()));
        // ?????? maxUpdateTime ??????
        Date maxUpdateTime = (Date) getFieldValue(smsTemplateService, "maxUpdateTime");
        assertEquals(max(smsTemplate01.getUpdateTime(), smsTemplate02.getUpdateTime()), maxUpdateTime);
    }

    @Test
    public void testParseTemplateContentParams() {
        // ????????????
        String content = "????????????????????????{operation}?????????????????????{code}";
        // mock ??????

        // ??????
        List<String> params = smsTemplateService.parseTemplateContentParams(content);
        // ??????
        assertEquals(Lists.newArrayList("operation", "code"), params);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateSmsTemplate_success() {
        // ????????????
        SysSmsTemplateCreateReqVO reqVO = randomPojo(SysSmsTemplateCreateReqVO.class, o -> {
            o.setContent("????????????????????????{operation}?????????????????????{code}");
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SysSmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        });
        // mock Channel ?????????
        SysSmsChannelDO channelDO = randomPojo(SysSmsChannelDO.class, o -> {
            o.setId(reqVO.getChannelId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelDO.getId()))).thenReturn(channelDO);
        // mock ?????? API ??????????????????
        when(smsClientFactory.getSmsClient(eq(reqVO.getChannelId()))).thenReturn(smsClient);
        when(smsClient.getSmsTemplate(eq(reqVO.getApiTemplateId()))).thenReturn(randomPojo(SmsCommonResult.class, SmsTemplateRespDTO.class,
                o -> o.setCode(GlobalErrorCodeConstants.SUCCESS.getCode())));

        // ??????
        Long smsTemplateId = smsTemplateService.createSmsTemplate(reqVO);
        // ??????
        assertNotNull(smsTemplateId);
        // ?????????????????????????????????
        SysSmsTemplateDO smsTemplate = smsTemplateMapper.selectById(smsTemplateId);
        assertPojoEquals(reqVO, smsTemplate);
        assertEquals(Lists.newArrayList("operation", "code"), smsTemplate.getParams());
        assertEquals(channelDO.getCode(), smsTemplate.getChannelCode());
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateSmsTemplate_success() {
        // mock ??????
        SysSmsTemplateDO dbSmsTemplate = randomSmsTemplateDO();
        smsTemplateMapper.insert(dbSmsTemplate);// @Sql: ?????????????????????????????????
        // ????????????
        SysSmsTemplateUpdateReqVO reqVO = randomPojo(SysSmsTemplateUpdateReqVO.class, o -> {
            o.setId(dbSmsTemplate.getId()); // ??????????????? ID
            o.setContent("????????????????????????{operation}?????????????????????{code}");
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SysSmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        });
        // mock ??????
        SysSmsChannelDO channelDO = randomPojo(SysSmsChannelDO.class, o -> {
            o.setId(reqVO.getChannelId());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelDO.getId()))).thenReturn(channelDO);
        // mock ?????? API ??????????????????
        when(smsClientFactory.getSmsClient(eq(reqVO.getChannelId()))).thenReturn(smsClient);
        when(smsClient.getSmsTemplate(eq(reqVO.getApiTemplateId()))).thenReturn(randomPojo(SmsCommonResult.class, SmsTemplateRespDTO.class,
                o -> o.setCode(GlobalErrorCodeConstants.SUCCESS.getCode())));

        // ??????
        smsTemplateService.updateSmsTemplate(reqVO);
        // ????????????????????????
        SysSmsTemplateDO smsTemplate = smsTemplateMapper.selectById(reqVO.getId()); // ???????????????
        assertPojoEquals(reqVO, smsTemplate);
        assertEquals(Lists.newArrayList("operation", "code"), smsTemplate.getParams());
        assertEquals(channelDO.getCode(), smsTemplate.getChannelCode());
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    public void testUpdateSmsTemplate_notExists() {
        // ????????????
        SysSmsTemplateUpdateReqVO reqVO = randomPojo(SysSmsTemplateUpdateReqVO.class);

        // ??????, ???????????????
        assertServiceException(() -> smsTemplateService.updateSmsTemplate(reqVO), SMS_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testDeleteSmsTemplate_success() {
        // mock ??????
        SysSmsTemplateDO dbSmsTemplate = randomSmsTemplateDO();
        smsTemplateMapper.insert(dbSmsTemplate);// @Sql: ?????????????????????????????????
        // ????????????
        Long id = dbSmsTemplate.getId();

        // ??????
        smsTemplateService.deleteSmsTemplate(id);
       // ????????????????????????
       assertNull(smsTemplateMapper.selectById(id));
        // ????????????
        verify(smsProducer, times(1)).sendSmsTemplateRefreshMessage();
    }

    @Test
    public void testDeleteSmsTemplate_notExists() {
        // ????????????
        Long id = randomLongId();

        // ??????, ???????????????
        assertServiceException(() -> smsTemplateService.deleteSmsTemplate(id), SMS_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testGetSmsTemplatePage() {
       // mock ??????
       SysSmsTemplateDO dbSmsTemplate = randomPojo(SysSmsTemplateDO.class, o -> { // ???????????????
           o.setType(SysSmsTemplateTypeEnum.PROMOTION.getType());
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
           o.setCode("yudaoyuanma");
           o.setContent("????????????");
           o.setApiTemplateId("yunai");
           o.setChannelId(1L);
           o.setCreateTime(buildTime(2021, 11, 11));
       });
       smsTemplateMapper.insert(dbSmsTemplate);
       // ?????? type ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setType(SysSmsTemplateTypeEnum.VERIFICATION_CODE.getType())));
       // ?????? status ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // ?????? code ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setCode("yuanma")));
       // ?????? content ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setContent("??????")));
       // ?????? apiTemplateId ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setApiTemplateId("nai")));
       // ?????? channelId ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setChannelId(2L)));
       // ?????? createTime ?????????
       smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setCreateTime(buildTime(2021, 12, 12))));
       // ????????????
       SysSmsTemplatePageReqVO reqVO = new SysSmsTemplatePageReqVO();
       reqVO.setType(SysSmsTemplateTypeEnum.PROMOTION.getType());
       reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
       reqVO.setCode("yudao");
       reqVO.setContent("??????");
       reqVO.setApiTemplateId("yu");
       reqVO.setChannelId(1L);
       reqVO.setBeginCreateTime(buildTime(2021, 11, 1));
       reqVO.setEndCreateTime(buildTime(2021, 12, 1));

       // ??????
       PageResult<SysSmsTemplateDO> pageResult = smsTemplateService.getSmsTemplatePage(reqVO);
       // ??????
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSmsTemplate, pageResult.getList().get(0));
    }

    @Test
    public void testGetSmsTemplateList() {
        // mock ??????
        SysSmsTemplateDO dbSmsTemplate = randomPojo(SysSmsTemplateDO.class, o -> { // ???????????????
            o.setType(SysSmsTemplateTypeEnum.PROMOTION.getType());
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCode("yudaoyuanma");
            o.setContent("????????????");
            o.setApiTemplateId("yunai");
            o.setChannelId(1L);
            o.setCreateTime(buildTime(2021, 11, 11));
        });
        smsTemplateMapper.insert(dbSmsTemplate);
        // ?????? type ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setType(SysSmsTemplateTypeEnum.VERIFICATION_CODE.getType())));
        // ?????? status ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // ?????? code ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setCode("yuanma")));
        // ?????? content ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setContent("??????")));
        // ?????? apiTemplateId ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setApiTemplateId("nai")));
        // ?????? channelId ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setChannelId(2L)));
        // ?????? createTime ?????????
        smsTemplateMapper.insert(ObjectUtils.clone(dbSmsTemplate, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // ????????????
        SysSmsTemplateExportReqVO reqVO = new SysSmsTemplateExportReqVO();
        reqVO.setType(SysSmsTemplateTypeEnum.PROMOTION.getType());
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCode("yudao");
        reqVO.setContent("??????");
        reqVO.setApiTemplateId("yu");
        reqVO.setChannelId(1L);
        reqVO.setBeginCreateTime(buildTime(2021, 11, 1));
        reqVO.setEndCreateTime(buildTime(2021, 12, 1));

       // ??????
       List<SysSmsTemplateDO> list = smsTemplateService.getSmsTemplateList(reqVO);
       // ??????
       assertEquals(1, list.size());
       assertPojoEquals(dbSmsTemplate, list.get(0));
    }

    @Test
    public void testCheckSmsChannel_success() {
        // ????????????
        Long channelId = randomLongId();
        // mock ??????
        SysSmsChannelDO channelDO = randomPojo(SysSmsChannelDO.class, o -> {
            o.setId(channelId);
            o.setStatus(CommonStatusEnum.ENABLE.getStatus()); // ?????? status ???????????????????????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelId))).thenReturn(channelDO);

        // ??????
        SysSmsChannelDO returnChannelDO = smsTemplateService.checkSmsChannel(channelId);
        // ??????
        assertPojoEquals(returnChannelDO, channelDO);
    }

    @Test
    public void testCheckSmsChannel_notExists() {
        // ????????????
        Long channelId = randomLongId();

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsChannel(channelId),
                SMS_CHANNEL_NOT_EXISTS);
    }

    @Test
    public void testCheckSmsChannel_disable() {
        // ????????????
        Long channelId = randomLongId();
        // mock ??????
        SysSmsChannelDO channelDO = randomPojo(SysSmsChannelDO.class, o -> {
            o.setId(channelId);
            o.setStatus(CommonStatusEnum.DISABLE.getStatus()); // ?????? status ?????????????????????
        });
        when(smsChannelService.getSmsChannel(eq(channelId))).thenReturn(channelDO);

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsChannel(channelId),
                SMS_CHANNEL_DISABLE);
    }

    @Test
    public void testCheckDictDataValueUnique_success() {
        // ???????????????
        smsTemplateService.checkSmsTemplateCodeDuplicate(randomLongId(), randomString());
    }

    @Test
    public void testCheckSmsTemplateCodeDuplicate_valueDuplicateForCreate() {
        // ????????????
        String code = randomString();
        // mock ??????
        smsTemplateMapper.insert(randomSmsTemplateDO(o -> o.setCode(code)));

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsTemplateCodeDuplicate(null, code),
                SMS_TEMPLATE_CODE_DUPLICATE, code);
    }

    @Test
    public void testCheckDictDataValueUnique_valueDuplicateForUpdate() {
        // ????????????
        Long id = randomLongId();
        String code = randomString();
        // mock ??????
        smsTemplateMapper.insert(randomSmsTemplateDO(o -> o.setCode(code)));

        // ?????????????????????
        assertServiceException(() -> smsTemplateService.checkSmsTemplateCodeDuplicate(id, code),
                SMS_TEMPLATE_CODE_DUPLICATE, code);
    }

    // ========== ???????????? ==========

    @SafeVarargs
    private static SysSmsTemplateDO randomSmsTemplateDO(Consumer<SysSmsTemplateDO>... consumers) {
        Consumer<SysSmsTemplateDO> consumer = (o) -> {
            o.setStatus(randomEle(CommonStatusEnum.values()).getStatus()); // ?????? status ?????????
            o.setType(randomEle(SysSmsTemplateTypeEnum.values()).getType()); // ?????? type ??? ??????
        };
        return randomPojo(SysSmsTemplateDO.class, ArrayUtils.append(consumer, consumers));
    }

}
