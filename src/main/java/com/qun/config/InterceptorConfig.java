package com.qun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

//    @Bean("localeResolver")
//    public LocaleResolver localeResolver(){
//        return new MyLocaleResolver();
//    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JwtInterceptor())
//                .addPathPatterns("/**")//拦截所有请求
//                .excludePathPatterns("/login","/static/**","/upload/**");//除了这些
//    }

}
