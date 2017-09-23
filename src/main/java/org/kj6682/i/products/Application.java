package org.kj6682.i.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

    @Data
    static class Category {

        Category(String name) {
            this.name = name;
        }

        private String name;

    }

    @Data
    static class CategoryName {

        private String category;
        private String name;

        public CategoryName(String category, String name) {
            this.category = category;
            this.name = name;
        }
    }

    @Controller
    static class ListController {

        Function<Object, Name> toName = new Function<Object, Name>() {
            public Name apply(Object t) {
                return new Name(t.toString());
            }
        };

        Function<Object, Category> toCategory = new Function<Object, Category>() {
            public Category apply(Object t) {
                return new Category(t.toString());
            }
        };

        Function<Object[], CategoryName> toCategoryName = new Function<Object[], CategoryName>() {
            public CategoryName apply(Object[] t) {
                return new CategoryName(t[0].toString(), t[1].toString());
            }
        };

        @Autowired
        private ProductAttributesRepository repository;

        @ResponseBody
        @RequestMapping("/api/listnames")
        public List<Name> listNames() {

            List<Object> objects = repository.listNames();

            List<Name> names = objects.stream()
                    .map(toName)
                    .collect(Collectors.<Name>toList());

            return names;

        }

        @ResponseBody
        @RequestMapping("/api/listcategories")
        public List<Category> listCategories() {

            List<Object> objects = repository.listCategories();

            List<Category> categories = objects.stream()
                    .map(toCategory)
                    .collect(Collectors.<Category>toList());

            return categories;

        }

        @ResponseBody
        @RequestMapping("/api/listcategoriesandnames")
        public List<CategoryName> listCategoriesAndNames() {

            List<Object[]> objects = repository.listCategoriesAndNames();

            List<CategoryName> categories = objects.stream()
                    .map(toCategoryName)
                    .collect(Collectors.<CategoryName>toList());

            return categories;

        }
    }

    @Data
    static class Name {

        public Name(String name) {
            this.name = name;
        }

        private String name;

    }
}
