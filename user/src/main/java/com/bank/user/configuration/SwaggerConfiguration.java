package com.bank.user.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }

	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	    		.select()
	            .paths(PathSelectors.ant("/user/**"))
	            .apis(RequestHandlerSelectors.basePackage("com.bank.user.services"))
	            .build()
	            .apiInfo(apiInfo())
	            .globalOperationParameters(parameterBuilder());
	}

	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfoBuilder().description("Omniron User Api").title("User API").build();
	    return apiInfo;
	}
	
	private List<springfox.documentation.service.Parameter> parameterBuilder() {
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<springfox.documentation.service.Parameter> aParameters = new ArrayList<springfox.documentation.service.Parameter>();
        aParameters.add( aParameterBuilder.build());
        return aParameters;
	}
}
