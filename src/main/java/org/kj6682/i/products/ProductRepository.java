package org.kj6682.i.products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCaseOrderByName(@Param("name") String name);

    List<Product> findByNameStartsWith(@Param("name") String name);

    List<Product> findByCategory(@Param("category") String category);

    List<Product> findByProducer(@Param("producer") String producer);
}


