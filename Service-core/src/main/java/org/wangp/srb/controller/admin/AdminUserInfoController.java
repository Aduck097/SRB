package org.wangp.srb.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wangp.common.exception.Assert;
import org.wangp.common.result.R;
import org.wangp.common.result.ResponseEnum;
import org.wangp.srb.mapper.UserInfoMapper;
import org.wangp.srb.pojo.UserInfo;
import org.wangp.srb.pojo.UserLoginRecord;
import org.wangp.srb.pojo.vo.UserInfoQuery;
import org.wangp.srb.service.UserInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021/8/7
 *
 * @author PingW
 */
@Api("会员管理模块")
@RestController
@RequestMapping("/admin/core/userInfo/")
@Slf4j
public class AdminUserInfoController {

    @Resource
    private UserInfoService userInfoService;


    @ApiOperation("分页查询")
    @GetMapping("list/{page}/{limit}")
    public R selectAll(
            // @RequestBody
            @ApiParam(value = "页码page",required = true) @PathVariable Long page,
            @ApiParam(value = "页数",required = true) @PathVariable Long limit,
            @ApiParam(value = "查询条件",required = false)  UserInfoQuery userInfoQuery){

        log.info("判断page是否为空"+page);
        Assert.notNull(page, ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        log.info("判断limit是否为空"+limit);
        Assert.notNull(limit, ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        // log.info("判断userInfoQuery是否为空"+userInfoQuery.toString());
        // Assert.notNull(userInfoQuery, ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        log.info("调用service查询所有会员");
        IPage<UserInfo> pageModel = userInfoService.selectAll(page,limit,userInfoQuery);
        log.info("判断返回的userInfoList");
        Assert.notNull(pageModel,ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        log.info("查询所有会员成功");
        return R.ok().message("").data("pageModel",pageModel);
    }

    @ApiOperation(value = "用户状态操作",notes = "用前端的传的用户Id来设置用户指定的状态")
    @PutMapping("lock/{id}/{status}")
    public R lockAndUnlock(
            @ApiParam("用户id") @PathVariable Long id,
            @ApiParam("用户状态") @PathVariable Integer status
    ){
        log.info("判断锁的id是否为空");
        Assert.notNull(id,ResponseEnum.WEIXIN_FETCH_USERINFO_ERROR);
        log.info("调用service，设置用户状态");
        userInfoService.lockAndUnlock(id,status);
        log.info("设置成功");
        return R.ok().message("更改成功");

    }

    @ApiOperation(value = "用户登录日志查询",notes = "使用id查询用户的日志")
    @GetMapping("userLoginRecordTop50/{userId}")
    public R userLoginRecordTop(@ApiParam("用户id用来查询登录日志") @PathVariable Long userId){
        log.info("判断查询日志的id是否为空");
        Assert.notNull(userId,ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        List<UserLoginRecord> userLoginRecordList = userInfoService.selectRecords(userId);
        log.info("查询日志成功");
        return R.ok().message("查询成功").data("list",userLoginRecordList);
    }
    @ApiOperation("校验手机号是否注册")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile){
        log.info("开始校验手机号是否注册");
        return userInfoService.checkMobile(mobile);
    }
}
