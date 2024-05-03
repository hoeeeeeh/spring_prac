package com.example.springbootex;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@MyComponent
@Service
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name){
        return "Hello " + name;
    }
}
