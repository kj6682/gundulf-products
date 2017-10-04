package org.kj6682.i.products;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luigi on 30/07/2017.
 */
@Api(value = "products", description = "Products API")
@RestController
@RequestMapping("/api")
class Controller {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    List<Product> list() {
        return productRepository.findAll();

    }

    @PostMapping(value = "/products")
    ResponseEntity<?> create(@RequestBody Product product) {

        Assert.notNull(product, "Product can not be empty");

        Product result = productRepository.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/products/{id}")
    void delete(@PathVariable(required = true) Long id) {
        productRepository.delete(id);
    }

    private static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(String id) {
            super("could not find product '" + id + "'.");
        }
    }

}
