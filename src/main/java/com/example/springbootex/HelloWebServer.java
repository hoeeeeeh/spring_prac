package com.example.springbootex;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
@ComponentScan
// 컴포넌트 어노테이션이 붙은 클래스를 찾아서 빈으로 등록 해줘
public class HelloWebServer {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        // tomcat 이 아닌 다른 servlet 이 사용할 수 도 있으니까, return type 은 추상화된 Type 을 이용하는게 좋다
        return new TomcatServletWebServerFactory();
    }
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }


    public static void main(String[] args) {

        SpringApplication.run(HelloWebServer.class, args);

    }


}
