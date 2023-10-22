package br.dev.jstec.efurniture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"BUCKET_NAME=testBucket"})
@ActiveProfiles("test")
class ApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {

        assertThat(context).isNotNull();
    }
}
