package org.wangp.srb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.wangp.common.exception.Assert;
import org.wangp.common.exception.BusinessException;
import org.wangp.common.result.ResponseEnum;
import org.wangp.common.utils.MD5;
import org.wangp.srb.mapper.UserAccountMapper;
import org.wangp.srb.mapper.UserLoginRecordMapper;
import org.wangp.srb.pojo.RegisterVO;
import org.wangp.srb.pojo.UserAccount;
import org.wangp.srb.pojo.UserInfo;
import org.wangp.srb.mapper.UserInfoMapper;
import org.wangp.srb.pojo.UserLoginRecord;
import org.wangp.srb.pojo.vo.LoginVO;
import org.wangp.srb.pojo.vo.UserInfoQuery;
import org.wangp.srb.pojo.vo.UserInfoVO;
import org.wangp.srb.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wangp.srb.utils.JwtUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Slf4j
@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserAccountMapper userAccountMapper;
    @ApiParam("用户注册服务模块")
    @Override
    public void register(RegisterVO registerVO) {
        log.info("准备注册");
        // 判断手机号是否已经注册
        QueryWrapper<UserInfo> mobileQueryWrapper = new QueryWrapper<>();
        mobileQueryWrapper.eq("mobile",registerVO.getMobile());
        UserInfo userInfoBySelect = userInfoMapper.selectOne(mobileQueryWrapper);
        if (!(null==userInfoBySelect)){
            throw new BusinessException(ResponseEnum.MOBILE_EXIST_ERROR);
        }

        // 手机号未被注册开始创建userInfo对象 将他传入到数据库
        UserInfo userInfo = new UserInfo();
        try {
        userInfo.setHeadImg("https://wangping-bucket-img.oss-cn-shenzhen.aliyuncs.com/test/2021/08/05/04c8c7b3-dda9-487b-b441-2cea1064bf9fdiscuss_1622856234031.jpeg");
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setName(registerVO.getMobile());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfoMapper.insert(userInfo);
        }catch (Exception e){
            throw new BusinessException(ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        }

        // 创建用户账户
        try {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
        }catch (Exception e){
            throw new BusinessException(ResponseEnum.WEIXIN_CALLBACK_PARAM_ERROR);
        }
    }
    @ApiParam("用户登录模块")
    @Override
    public UserInfoVO login(LoginVO loginVO,String ip) {
        String mobile = loginVO.getMobile();
        Integer userType = loginVO.getUserType();
        String password = loginVO.getPassword();
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        log.info("查询手机号"+mobile);
        userInfoQueryWrapper.eq("mobile",mobile);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        Assert.notNull(userInfo,ResponseEnum.LOGIN_MOBILE_ERROR);
        log.info("判断是否为借款人");
        Assert.isTrue(!userType.equals(userInfo.getUserType()),ResponseEnum.LOGIN_MOBILE_ERROR);
        log.info("判断密码是否正确");
        Assert.equalsWp(password,userInfo.getPassword(),ResponseEnum.LOGIN_PASSWORD_ERROR);
        log.info("判断用户是否被锁");
        Assert.equalsWp(UserInfo.STATUS_LOCKED,userInfo.getStatus(),ResponseEnum.LOGIN_LOKED_ERROR);

        //记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordMapper.insert(userLoginRecord);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setName(userInfo.getName());
        userInfoVO.setNickName(userInfo.getNickName());
        log.info("设置登录令牌");
        JwtUtils.createToken(userInfo.getId(),userInfo.getName());
        // 设置jWt令牌
        userInfoVO.setToken(userInfo.getNickName());
        userInfoVO.setUserType(userInfo.getUserType());
        return userInfoVO;
    }
    @ApiOperation(value = "查询所有和条件查询",notes = "返回Ipage分页类型" )
    @Override
    public IPage<UserInfo> selectAll(Long page, Long limit, UserInfoQuery userInfoQuery) {
        Page pageQuery = new Page<>(page,limit);
        if(null==userInfoQuery){
            log.info("无需条件查询，返回所有");
            return userInfoMapper.selectPage(pageQuery,null);
        }
        log.info("条件查询");
        // 使用条件查询
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq(StringUtils.isNotBlank(userInfoQuery.getMobile()),"mobile",userInfoQuery.getMobile())
                            .eq(null!=userInfoQuery.getStatus(),"status",userInfoQuery.getStatus())
                            .eq(null!=userInfoQuery.getUserType(),"user_type",userInfoQuery.getUserType());
        log.info("service返回查询结果");
        return userInfoMapper.selectPage(pageQuery,userInfoQueryWrapper);
    }

    @Override
    public void lockAndUnlock(Long id, Integer status) {
        // 先查询
        log.info("通过用户id为"+id+"查询");
        UserInfo userInfo = userInfoMapper.selectById(id);
        log.info("设置用户状态为"+status);
        userInfo.setStatus(status);
        log.info("修改用户状态成功修改成功:"+status);
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public List<UserLoginRecord> selectRecords(Long userId) {
        QueryWrapper<UserLoginRecord> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("user_id",userId)
                .orderByDesc("id")
                .last("limit 50");
        List<UserLoginRecord> userLoginRecordList = userLoginRecordMapper.selectList(objectQueryWrapper);
        return userLoginRecordList;
    }

    @Override
    public boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = userInfoMapper.selectCount(queryWrapper);
        log.info("手机号注册结果"+ (count>0));
        return count > 0;
    }


}
