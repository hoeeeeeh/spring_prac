package com.example.springbootex;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@SpringBootApplication
//구성 정보를 가지고 있는 Class 이다. Spring Container 가 이 어노테이션을 보고, 빈 오브젝트가 있겠구나 라고 파악할 수 있다.
// Configuration 어노테이션이 붙은 클래스가 AnnotationConfigWebApplicationContext 에 처음 등록된다는 사실이 중요하다.
@Configuration
public class HelloWebServer {
    // Factory Method : 어떤 오브젝트를 생성하는 로직을 담고 있는 ..
    // Factory Method 로 Bean 오브젝트 생성 하고, DI 등을 수행하고, 스프링 컨테이너에게 빈으로 등록해서 이후에 사용한다고 알려주려고 한다.

    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }

    @Bean
    // HelloService 라는 인터페이스를 만들었으므로 return 을 HelloService 로.
    public HelloService helloService() {
        return new SimpleHelloService();
    }
    public static void main(String[] args) {

        //GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
        //GenericWebApplication 은 Java 코드로 만든 Configuration 정보를 읽을 수가 없다.
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
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
        /*  GenericWebApplicationContext 에 Bean 등록
            applicationContext.registerBean(SimpleHelloService.class);
            applicationContext.registerBean(HelloController.class);
        */

        // AnnotationConfigWebApplicationContext 에 Java 코드로 된 구성 정보를 가지고 있는 클래스를 한 번 등록 해줘야 한다.
        applicationContext.register(HelloWebServer.class);
        // 스프링 컨테이너의 초기화 작업
        applicationContext.refresh();

    }

}
