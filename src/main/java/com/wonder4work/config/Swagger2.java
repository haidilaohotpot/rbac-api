package com.wonder4work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @since 1.0.0 2020/3/29
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        // 官方提供的文档地址 http://localhost:8088/swagger-ui.html

        // /doc.html 第三方路径
        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为2
                .apiInfo(apiInfo())                                // 用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.wonder4work.controller")) //指定controller包
                .paths(PathSelectors.any()) // 所有的controller
                .build();


    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("权限管理接口API")  // 文档页标题
                .contact(new Contact("xiezengcheng", "http://www.wonder4work.com", "xiezengchengtx163.com"))
                .description("专为此权限管理系统提供的API文档")
                .version("1.0.1")  //文档版本号
                .termsOfServiceUrl("http://api.rbac.wonder4work.com")
                .build();
    }


}
