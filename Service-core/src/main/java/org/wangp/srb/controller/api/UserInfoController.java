package org.wangp.srb.controller.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.wangp.common.exception.Assert;
import org.wangp.common.result.R;
import org.wangp.common.result.ResponseEnum;
import org.wangp.common.utils.RegexValidateUtils;
import org.wangp.srb.pojo.RegisterVO;
import org.wangp.srb.pojo.UserInfo;
import org.wangp.srb.pojo.vo.LoginVO;
import org.wangp.srb.pojo.vo.UserInfoVO;
import org.wangp.srb.service.UserAccountService;
import org.wangp.srb.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.AlgorithmConstraints;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Api("用户注册")
@RestController
@RequestMapping("/userInfo/core")
@Slf4j
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiParam(value = "用户注册",required = true)
    @PostMapping("register")
    public R registerCount(@RequestBody RegisterVO registerVO){
        // 判断手机号是否为空
        Assert.notNull(registerVO.getMobile(),ResponseEnum.MOBILE_NULL_ERROR);
        // 判断手机号是否合法
        Assert.isTrue(RegexValidateUtils.checkCellphone(registerVO.getMobile()),ResponseEnum.MOBILE_ERROR);
        // 判断密码是否为空
        Assert.notNull(registerVO.getPassword(),ResponseEnum.PASSWORD_NULL_ERROR);
        // 判断验证码是否一致
        redisTemplate.opsForValue().set("srb:sms:code:" + registerVO.getMobile(),registerVO.getCode());
        Object o = redisTemplate.opsForValue().get("srb:sms:code:" + registerVO.getMobile());
        System.out.println("o.toString() = " + o.toString());
        Assert.hasCode(redisTemplate.opsForValue().get("srb:sms:code:"+registerVO.getMobile()),
                registerVO.getCode(), ResponseEnum.CODE_ERROR);
        try {
            userInfoService.register(registerVO);
            return R.ok().message("恭喜你，成功成为了韭菜");
        }catch (Exception e){
            log.error(e.getMessage());
            return R.error().message("恭喜你注册失败，service出现异常"+e.getMessage());
        }


    }
    @ApiParam(value = "用户登录API",required = true)
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVO loginVO,HttpServletRequest request){
        // 判断获取的值是否有问题
        Assert.notNull(loginVO.getMobile(),ResponseEnum.MOBILE_NULL_ERROR);
        // Assert.isTrue(!(loginVO.getUserType()==1),ResponseEnum.LOGIN_MOBILE_ERROR);
        Assert.notNull(loginVO.getPassword(),ResponseEnum.PASSWORD_NULL_ERROR);

        String remoteAddr = request.getRemoteAddr();

        // 需要调用service方法
        UserInfoVO userInfoVO = userInfoService.login(loginVO,remoteAddr);
        log.info("登录成功");
        return R.ok().message("登录成功").data("userInfo",userInfoVO);
    }

}