package org.kj6682.i.products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);

    List<Product> findByCategory(@Param("category") String category);
}
