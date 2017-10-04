package org.kj6682.i.products;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class QueryAttributes {
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
