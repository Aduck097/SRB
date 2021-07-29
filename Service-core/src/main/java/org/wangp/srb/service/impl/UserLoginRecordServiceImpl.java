package org.wangp.srb.service.impl;

import org.wangp.srb.pojo.UserLoginRecord;
import org.wangp.srb.mapper.UserLoginRecordMapper;
import org.wangp.srb.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
