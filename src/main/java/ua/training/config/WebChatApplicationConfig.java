package ua.training.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by dima on 23.02.17.
 */
@EnableWebMvc
@Configuration
@ComponentScan({"ua.training.controller", "ua.training.service"})
public class WebChatApplicationConfig {
}
