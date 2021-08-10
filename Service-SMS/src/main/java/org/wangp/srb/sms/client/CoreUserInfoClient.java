package org.wangp.srb.sms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wangp.srb.sms.client.fallback.CoreUserInfoClientFallback;

@FeignClient(value = "Service-core",fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient {

    @GetMapping("/admin/core/userInfo/checkMobile/{mobile}")
    boolean checkMobile(@PathVariable String mobile);
}