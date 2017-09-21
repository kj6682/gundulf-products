package org.kj6682.i.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Profile({"ddl-create", "h2"})
	@Bean
	CommandLineRunner initProducts(ProductRepository productRepository) throws IOException{

	    /* It is not possible to use Spring ResourceUtils when reading resources files
	       from a jar.
	       So we should get back to basic read via BufferedReader and streams..

	        https://stackoverflow.com/questions/5516020/bufferedreader-read-multiple-lines-into-a-single-string

	        comment@Grzegorz Gajos
	     */
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
}
