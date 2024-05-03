package com.example.springbootex;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// annotation 을 처음 생성하면, 꼭 붙여줘야 하는 두 가지 메타 에노테이션
// Retention : 이 애노테이션이 어디까지 살아 있을 것인가? 일단 Runtime 이라고 지정.
// Target : 애노테이션을 적용할 대상을 지정
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface MyComponent {
}
