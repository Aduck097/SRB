package org.wangp.srb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 2021/7/29
 *
 * @author PingW
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     *  Docket swagger2的文档对象
     * @return
     */
    @Bean
    public Docket apiConfig(){

        return new Docket(DocumentationType.SWAGGER_2);

    }
}
