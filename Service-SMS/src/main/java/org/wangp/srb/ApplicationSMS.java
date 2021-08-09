package org.wangp.srb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2021/8/3
 *
 * @author PingW
 */
@SpringBootApplication
@ComponentScan({"org.wangp.srb","org.wangp.common"})
public class ApplicationSMS {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSMS.class,args);
    }
}
