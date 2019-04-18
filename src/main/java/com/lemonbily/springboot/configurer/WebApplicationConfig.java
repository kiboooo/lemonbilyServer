package com.lemonbily.springboot.configurer;

import com.lemonbily.springboot.configurer.Intercept.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //移除登陆，注册，以及与播放视频相关的请求路径验证
        String[] commonEx = {
                "/LoginController/registered","/LoginController/login",
                "/SeriesController/**","/VideoController/**"};

        //增加token验证拦截器，处理以lemonbily为请求路径的需要token权限的信息。
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**").excludePathPatterns(commonEx);
    }
}
