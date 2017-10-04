package org.kj6682.i.products;

import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(value = "products attributes", description = "Products List of Attributes API")
@RestController
@RequestMapping("/api/products/list")
class ListController {

    Function<Object, QueryAttributes.Name> toName = new Function<Object, QueryAttributes.Name>() {
        public QueryAttributes.Name apply(Object t) {
            return new QueryAttributes.Name(t.toString());
        }
    };

    Function<Object, QueryAttributes.Category> toCategory = new Function<Object, QueryAttributes.Category>() {
        public QueryAttributes.Category apply(Object t) {
            return new QueryAttributes.Category(t.toString());
        }
    };

    Function<Object[], QueryAttributes.CategoryName> toCategoryName = new Function<Object[], QueryAttributes.CategoryName>() {
        public QueryAttributes.CategoryName apply(Object[] t) {
            return new QueryAttributes.CategoryName(t[0].toString(), t[1].toString());
        }
    };

    @Autowired
    private ProductRepository repository;

    @GetMapping("/names")
    public List<QueryAttributes.Name> names() {

        List<Object> objects = repository.listNames();

        List<QueryAttributes.Name> names = objects.stream()
                .map(toName)
                .collect(Collectors.<QueryAttributes.Name>toList());

        return names;

    }

    @GetMapping("/categories")
    public List<QueryAttributes.Category> categories() {

        List<Object> objects = repository.listCategories();

        List<QueryAttributes.Category> categories = objects.stream()
                .map(toCategory)
                .collect(Collectors.<QueryAttributes.Category>toList());

        return categories;

    }

    @GetMapping("/categoriesandnames")
    public List<QueryAttributes.CategoryName> categoriesAndNames() {

        List<Object[]> objects = repository.listCategoriesAndNames();

        List<QueryAttributes.CategoryName> categories = objects.stream()
                .map(toCategoryName)
                .collect(Collectors.<QueryAttributes.CategoryName>toList());

        return categories;

    }

    static class QueryAttributes {
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

        @Data
        static class Name {

            public Name(String name) {
                this.name = name;
            }

            private String name;

        }
    }
}
