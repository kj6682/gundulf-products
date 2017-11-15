package org.kj6682.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
        It is not possible to use Spring ResourceUtils when reading resources files from a jar.
	    So we should get back to basic read via BufferedReader and streams..

	        https://stackoverflow.com/questions/5516020/bufferedreader-read-multiple-lines-into-a-single-string

	    comment@Grzegorz Gajos
	 */
    @Profile({"ddl-create", "h2"})
    @Bean
    CommandLineRunner initProducts(ProductRepository productRepository) throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource("products.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        final String jsonArray = reader.lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        Product[] asArray = mapper.readValue(jsonArray, Product[].class);

        return (evt) -> {
            Arrays.asList(asArray).forEach(
                    cake -> {
                        productRepository.save(cake);
                        System.out.println(cake);
                    }
            );

        };

    }

    @Profile({"ddl-create", "h2"})
    @Bean
    CommandLineRunner initItems(ItemRepository itemRepository) throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource("items.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        final String jsonArray = reader.lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        Item[] asArray = mapper.readValue(jsonArray, Item[].class);

        return (evt) -> {
            Arrays.asList(asArray).forEach(
                    cake -> {
                        itemRepository.save(cake);
                        System.out.println(cake);
                    }
            );

        };

    }

    @Value("${cors.pattern}")
    private String CORS_BASE_PATTERN;

    @Value("${cors.origin}")
    private String ALLOWED_ORIGINS;

    @Value("${cors.headers}")
    private String ALLOWED_HEADERS;

    @Value("${cors.methods}")
    private String ALLOWED_METHODS;



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(CORS_BASE_PATTERN).allowedOrigins(ALLOWED_ORIGINS).allowedMethods(ALLOWED_METHODS).allowedHeaders(ALLOWED_HEADERS);
            }
        };
    }

}
