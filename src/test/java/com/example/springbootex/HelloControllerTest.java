package com.example.springbootex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

public class HelloControllerTest {
    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("Test");
        Assertions.assertThat(ret).isEqualTo("Test");

    }
    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(name -> name);

        // String ret = helloController.hello(null);
        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
