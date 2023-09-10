package br.dev.jstec.basicproject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {

        assertThat(context).isNotNull();
    }
}
