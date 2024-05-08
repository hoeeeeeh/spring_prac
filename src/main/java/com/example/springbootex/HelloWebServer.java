package com.example.springbootex;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        //GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
        //GenericWebApplication 은 Java 코드로 만든 Configuration 정보를 읽을 수가 없다.
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // 생략하면 안됨. Generic Web Application Context 에서도 onRefresh hook 메소드를 확장해서 추가적인 작업을 하기 때문.

                //Spring container 에 등록한 Servlet 가져오기
                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                // dispatcherServlet 이 상속한 class 에 이미 setApplicationContext 와 유사한 일을 하는 함수가 존재하고,
                // 디스패처 서블릿이 등록 되는 시점에 스프링 컨테이너가 어플리케이션 컨텍스트를 주입해준다.
                // dispatcherServlet.setApplicationContext(this);


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
