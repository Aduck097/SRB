package org.wangp.srb.sms.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wangp.srb.sms.client.CoreUserInfoClient;

@Service
@Slf4j
public class CoreUserInfoClientFallback implements CoreUserInfoClient {
    @Override
    public boolean checkMobile(String mobile) {
        log.error("远程调用失败，服务熔断");
        return false;
    }
}