package org.wangp.srb.sms.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.wangp.common.exception.Assert;
import org.wangp.common.result.R;
import org.wangp.common.result.ResponseEnum;
import org.wangp.common.utils.RandomUtils;
import org.wangp.common.utils.RegexValidateUtils;
import org.wangp.srb.sms.client.CoreUserInfoClient;
import org.wangp.srb.sms.controller.SmsController;
import org.wangp.srb.sms.service.SmsService;
import org.wangp.srb.sms.util.SmsProperties;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 2021/8/5
 *
 * @author PingW
 */
@Api("阿里云短信服务")
@RestController
@RequestMapping("aliyun/sms")
public class SmsControllerImpl implements SmsController {
    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    @Resource
    private SmsService smsService;

    @ApiParam("输入手机号获取验证码")
    @GetMapping("/send/{mobile}")
    public R getMessage(
            @ApiParam(value = "用户的手机号",required = true)
            @PathVariable String mobile
    ){
        boolean result = coreUserInfoClient.checkMobile(mobile);
        System.out.println("result = " + result);
        Assert.isTrue(result == false, ResponseEnum.MOBILE_EXIST_ERROR);

        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile),ResponseEnum.MOBILE_ERROR);
        // 短信模板读取
        String templateCode = SmsProperties.TEMPLATE_CODE;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("code", RandomUtils.getSixBitRandom());
        // 不调用阿里云短信服务
        // String send = smsService.send(mobile, templateCode, paramMap);
        // Assert.eqCode(send,ResponseEnum.CODE_ERROR);
        return R.ok().message("验证码获取成功");

    }

}
