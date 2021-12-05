package com.bugprovider.seed.config;

import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableOpenApi
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Value("${knife4j.title}")
    private String title;

    @Value("${knife4j.description}")
    private String description;

    @Value("${knife4j.version}")
    private String version;

    @Value("${knife4j.author}")
    private String author;

    @Value("${knife4j.apiBasePackage}")
    private String apiBasePackage;


    @Bean(value = "defaultApi1")
    public Docket defaultApi1() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityContext> securityContexts = Arrays.asList(SecurityContext.builder()
                .securityReferences(CollectionUtil.newArrayList(new SecurityReference("Authorization", authorizationScopes)))
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        HttpAuthenticationScheme httpAuthenticationScheme = HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name(HttpHeaders.AUTHORIZATION)
                .description("Bearer Token")
                .build();
        securitySchemes.add(httpAuthenticationScheme);

        //默认全局参数
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder().name("test").description("测试").in(ParameterType.QUERY).required(true).build());

        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("1.0")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(apiBasePackage))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts).securitySchemes(securitySchemes);
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl("")
                .contact(new Contact(author, "", ""))
                .version(version)
                .build();
    }

}
