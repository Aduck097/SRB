package org.wangp.srb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.wangp.srb.pojo.RegisterVO;
import org.wangp.srb.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wangp.srb.pojo.UserLoginRecord;
import org.wangp.srb.pojo.vo.LoginVO;
import org.wangp.srb.pojo.vo.UserInfoQuery;
import org.wangp.srb.pojo.vo.UserInfoVO;

import java.util.List;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
public interface UserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);

    UserInfoVO login(LoginVO loginVO,String ip);

    IPage<UserInfo> selectAll(Long page, Long limit, UserInfoQuery userInfoQuery);

    void lockAndUnlock(Long id, Integer status);

    List<UserLoginRecord> selectRecords(Long userId);
}
