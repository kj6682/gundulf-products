package org.kj6682.products;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Api(value = "products", description = "Products API")
@RestController
@RequestMapping("/api/products/v2.0")
class Controller {

    @Autowired
    private ProductRepository repository;


    @GetMapping("/")
    List<Product> findProducts(
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "category", defaultValue = "", required = false) String category) {

        if (category.isEmpty() && name.isEmpty()) {
            return repository.findAll();
        }

        if (category.isEmpty()) {
            return repository.findByNameContainingIgnoreCaseOrderByName(name);
        }

        if (name.isEmpty()) {
            return repository.findByCategoryOrderByName(category);
        }

        return repository.findByNameContainingIgnoreCaseOrderByName(name)
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());

    }

    @GetMapping("/paged")
    List<Product> findProductPaged(
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "category", defaultValue = "", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "0") int size
    ) {
        isTrue(page > 0, "the page must be a positive number");
        isTrue(size >= 0, "the size must be a strictly positive number");

        List<Product> list = new ArrayList<>();
        repository.findByCategoryOrderByName(category,
                new PageRequest(page, size))
                .iterator()
                .forEachRemaining(list::add);

        return list;

    }

    @PostMapping(value = "/")
    ResponseEntity<?> create(@RequestBody Product product) {

        notNull(product, "Product can not be empty");
        isTrue(!isEmpty(product.getName()), "a product needs a name");
        isTrue(product.getStartDate().isBefore(product.getEndDate()), "start date should be before end date");

        product.setName(product.getName().toLowerCase().trim());

        Product result = repository.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/")
    void delete(@RequestParam(required = true) Long id) {

        notNull(id,"id must be not null");
        isTrue(id >=0, "id must be positive");

        repository.delete(id);
    }

    private static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(String id) {
            super("could not find product '" + id + "'.");
        }
    }

    @ControllerAdvice
    private static class ProductControllerAdvice {

        @ResponseBody
        @ExceptionHandler(ProductNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public List<Product> handleConflict() {
            return new LinkedList<Product>();
        }


        @ResponseBody
        @ExceptionHandler(java.lang.IllegalArgumentException.class)
        public ResponseEntity<?> handleConflictIllegalArgument() {
            return new ResponseEntity<>("Illegal Arguments", HttpStatus.FORBIDDEN);
        }

    }

}//:)
