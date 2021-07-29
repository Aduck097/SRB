package org.wangp.srb.service.impl;

import org.wangp.srb.pojo.UserAccount;
import org.wangp.srb.mapper.UserAccountMapper;
import org.wangp.srb.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
