package com.jagiya.main.config;

import com.jagiya.Application;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = Application.class)
public class FeignConfiguration {
}
