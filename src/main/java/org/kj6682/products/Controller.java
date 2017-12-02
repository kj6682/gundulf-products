package org.kj6682.products;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luigi on 30/07/2017.
 */
@Api(value = "products", description = "Products API")
@RestController
@RequestMapping("/api")
class Controller {

    @Autowired
    private ProductRepository repository;


    @GetMapping("/products/{producer}")
    List<Product> listByProducer(@PathVariable String producer, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "0") int size) {
        //TODO check the producer
        if (page == 0 && size == 0) {
            return repository.findByProducerOrderByName(producer);
        }

        List<Product> list = new ArrayList<>();
        repository.findByProducerOrderByName(producer, new PageRequest(page, size)).iterator().forEachRemaining(list::add);
        return list;

    }

    @GetMapping("/products/{producer}/search")
    List<Product> search(@PathVariable String producer,
                         @RequestParam(value = "name", defaultValue = "") String name) {
        //TODO check the producer
        return repository.findByNameContainingIgnoreCaseOrderByName(name)
                .stream()
                .filter(product -> product.getProducer().equals(producer))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/products/{producer}")
    ResponseEntity<?> create(@PathVariable String producer,
                             @RequestBody Product product) {
        Assert.notNull(product, "Product can not be empty");
        //TODO check the producer
        Product result = repository.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/products/{producer}/{id}")
    void delete(@PathVariable String producer,
                @PathVariable(required = true) Long id) {
        //TODO check the producer
        repository.delete(id);
    }

    private static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(String id) {
            super("could not find product '" + id + "'.");
        }
    }
}
