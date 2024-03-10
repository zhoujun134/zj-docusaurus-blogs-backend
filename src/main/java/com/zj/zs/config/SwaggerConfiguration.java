package com.zj.zs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger接口文档的配置
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration {

    /**
     *  表示所有的请求路径都将被映射到指定的资源路径中。
     */

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 分组名称
                .groupName("zs boot 开发者接口文档")
                .select()
                //这里标注控制器的位置
                .apis(RequestHandlerSelectors.basePackage("com.zj.zs"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api信息
     * @return api对象信息
     */
    private ApiInfo apiInfo()   {
        return new ApiInfoBuilder()
                .title("zs boot 开发者接口文档")  // 标题
                .description("zs boot 开发者接口文档")  // 简介
                .termsOfServiceUrl("http://localhost:8080/") // Swagger API文档中的一个可选配置项，用于指定API服务的服务条款URL。
                .contact(new Contact("zj","https://zbus.top","1161386101@qq.com"))
                .version("1.0")
                .build();
    }
}
