package org.kj6682.i.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Profile({"ddl-create", "h2"})
	@Bean
	CommandLineRunner initProducts(ProductRepository productRepository) throws IOException{

		File jsonFile = ResourceUtils.getFile("classpath:products.json");

		ObjectMapper mapper = new ObjectMapper();
		String jsonArray = new String(Files.readAllBytes(jsonFile.toPath()));

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
