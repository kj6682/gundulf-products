package org.kj6682.i.products;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCaseOrderByName(@Param("name") String name);

    List<Product> findByNameStartsWith(@Param("name") String name);

    List<Product> findByCategory(@Param("category") String category);

    List<Product> findByProducer(@Param("producer") String producer);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    void delete(Long id);

}
