package org.wangp.srb.sms.service;

import java.util.Map;

public interface SmsService {

    String send(String mobile, String templateCode, Map<String,Object> param);
}