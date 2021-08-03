package org.wangp.srb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2021/7/28
 *
 * @author PingW
 */
@SpringBootApplication
@ComponentScan({"org.wangp.common","org.wangp.srb"})
public class ApplicationSrb {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSrb.class,args);
    }
}
