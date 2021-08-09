package org.wangp.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2021/8/4
 *
 * @author PingW
 */
@SpringBootApplication
@ComponentScan({"org.wangp.srb","org.wangp.common"})
public class ApplicationOSS {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationOSS.class,args);
    }

}

