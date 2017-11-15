package org.kj6682.products;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 12/07/2017.
 *
 * TDD - use this test to define the ORDER model
 *
 */
@RunWith(SpringRunner.class)
@JsonTest
public class ItemJsonTest {

    @Autowired
    private JacksonTester<Item> json;

    @MockBean
    private ProductRepository productRepository;

    Item cake;

    File jsonFile;

    @Before
    public void setup() throws Exception{
        cake = new Item("product",
                "category",
                "producer");
        jsonFile = ResourceUtils.getFile("classpath:item.json");

    }
    @Test
    public void serialise() throws Exception{
        System.out.println(this.json.write(cake));
        assertThat(this.json.write(cake)).isEqualTo(jsonFile);
    }
    @Test
    public void deserialise() throws Exception {

        String jsonObject = new String(Files.readAllBytes(jsonFile.toPath()));
        Item newCake = this.json.parse(jsonObject).getObject();
        assertThat(newCake.equals(cake));

    }

}
