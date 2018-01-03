package org.kj6682.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;




@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = {"h2"})
public class ApplicationTest {

    @Autowired
    Controller impl;

    @Test
    public void contextLoads() throws Exception {
        assertThat(impl).isNotNull();
    }

}