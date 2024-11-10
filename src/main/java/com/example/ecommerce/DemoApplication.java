package com.example.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.ecommerce.HTTPInterceptor.HTTPinterceptor;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {
	@Autowired
	HTTPinterceptor httPinterceptor;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(httPinterceptor).excludePathPatterns("/login","/createuser");
    }

}
