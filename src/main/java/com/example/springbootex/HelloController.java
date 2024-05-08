package com.example.springbootex;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

//@RequestMapping("/hello")
//RestController 을 사용하면, 굳이 리퀘스트 맵핑을 넣지 않아도 디스패처 서블릿이 이 안에 맵핑 정보가 담겨 있을거라고 생각하고 메소드를 알아서
//뒤져보고 찾는다

//@Component
//@MyComponent
//@Controller
@RestController
// API로서 기능을 하는 컨트롤러 메소드를 만들 때는 컨트롤러 메소드가 리턴하는 값을 그대로 응답의 바디에 넣어서 리턴하는 방식을 주로 쓴다.
// 그래서 @ResponseBody 가 필요한데, RestController 의 메타 애노테이션에 들어있다.
public class HelloController {
    private final HelloService helloService;
    private final ApplicationContext applicationContext;
    public HelloController(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        this.applicationContext = applicationContext;
    }

    // /hello 로 들어오는 요청 중에서, Get 요청만 처리
    //@GetMapping

    //RestController 를 사용해서 리퀘스트 맵핑 없이, 메소드에 바로 url 매핑
    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}


