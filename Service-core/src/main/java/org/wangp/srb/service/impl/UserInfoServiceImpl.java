package org.wangp.srb.service.impl;

import org.wangp.srb.pojo.UserInfo;
import org.wangp.srb.mapper.UserInfoMapper;
import org.wangp.srb.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
