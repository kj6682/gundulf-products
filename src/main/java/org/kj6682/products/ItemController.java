package org.kj6682.products;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luigi on 30/07/2017.
 */
@Api(value = "items", description = "Items API")
@RestController
@RequestMapping("/api")
class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping("/items")
    List<Item> list(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "0") int size) {
        if (page == 0 && size == 0) {
            return repository.findAllByOrderByName();
        }

        List<Item> list = new ArrayList<>();
        repository.findAll(new PageRequest(page, size)).iterator().forEachRemaining(list::add);
        return list;

    }

    @GetMapping("/items/search")
    List<Item> search(@RequestParam(value = "name", defaultValue = "") String name) {
        return repository.findByNameContainingIgnoreCaseOrderByName(name);
    }

    @PostMapping(value = "/items")
    ResponseEntity<?> create(@RequestBody Item item) {

        Assert.notNull(item, "Item can not be empty");

        Item result = repository.save(item);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/items/{id}")
    void delete(@PathVariable(required = true) Long id) {
        repository.delete(id);
    }

    private static class ItemNotFoundException extends RuntimeException {
        ItemNotFoundException(String id) {
            super("could not find product '" + id + "'.");
        }
    }

}
