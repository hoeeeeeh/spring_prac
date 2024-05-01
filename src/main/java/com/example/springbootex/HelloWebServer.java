package com.example.springbootex;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@SpringBootApplication
public class HelloWebServer {

    public static void main(String[] args) {

        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // 생략하면 안됨. Generic Web Application Context 에서도 onRefresh hook 메소드를 확장해서 추가적인 작업을 하기 때문.
                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");
                    // slash 밑으로 들어오는 모든 요청을 처리하겠다.
                });
                webServer.start();

            }
        };
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.registerBean(HelloController.class);

        // 스프링 컨테이너의 초기화 작업
        applicationContext.refresh();

    }

}
