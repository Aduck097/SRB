package org.wangp.srb.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();

    }
    private ApiInfo adminApiInfo() {
        return  new ApiInfoBuilder().title("尚融宝后台管理系统-API文档")
                .description("本文档描述了尚融宝后台管理系统接口")
                .version("1.0")
                .contact(new Contact("wangping", "https://github.com/Aduck097/SRB", "822371090@qq.com"))
                .build();

    }

    @Bean
    public Docket webConfig(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("webIntegerApi")
                .apiInfo(webConfigInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/IntegralGradeController.*")))
                .build();
    }

    private ApiInfo webConfigInfo() {
        return new ApiInfoBuilder().title("")
                .description("本文档描述了尚融宝Web管理系统接口")
                .version("1.0")
                .contact(new Contact("wangping", "https://github.com/Aduck097/SRB", "822371090@qq.com"))
                .build();
    }
}
