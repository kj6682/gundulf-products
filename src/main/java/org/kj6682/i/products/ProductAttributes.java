package org.kj6682.i.products;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProductAttributes {
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
