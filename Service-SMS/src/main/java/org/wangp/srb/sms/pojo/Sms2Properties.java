package org.wangp.srb.sms.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:sms.properties")///com/atguigu/srb/sms/
public class Sms2Properties {

    @Value("${region-id}")
    private  String regionId;

    public static final String keyId = "LTAI5tEDLT5vu27M2cBr6U5Z";

    @Bean
    public Sms2Properties createSms2Properties(){
        System.out.println(regionId);
        Sms2Properties sms2Properties = new Sms2Properties();
        sms2Properties.setRegionId(regionId);

        return sms2Properties;
    }
}
