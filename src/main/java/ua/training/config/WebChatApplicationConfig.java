package ua.training.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ua.training.intersceptor.ErrorInterceptor;

/**
 * Created by dima on 23.02.17.
 */
@EnableWebMvc
@Configuration
@ComponentScan({"ua.training.controller", "ua.training.service"})
public class WebChatApplicationConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new ErrorInterceptor());
    }
}
