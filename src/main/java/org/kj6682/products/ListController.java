package org.kj6682.products;

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

    static class QueryAttributes {

        @Data
        static class Name {

            public Name(String name) {
                this.name = name;
            }

            private String name;

        }
    }
}
