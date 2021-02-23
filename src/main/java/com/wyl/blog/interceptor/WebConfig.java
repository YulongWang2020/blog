package com.wyl.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + File.separator+ "images" +File.separator +"upload";
        String path = environment.getRequiredProperty("upload.path");
        registry.addResourceHandler(environment.getRequiredProperty("virtual.images.path")).addResourceLocations("file:"+"/" +path+"/");
    }
}
