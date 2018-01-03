package org.kj6682.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, ProductKey> {

    List<Product> findByProducerOrderByName(@Param("producer") String producer);
    List<Product> findByProducerOrderByName(@Param("producer") String producer, Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseOrderByName(@Param("name") String name);


    Page<Product> findAll(Pageable pageable);

    void delete(ProductKey key);

    @Query("select DISTINCT p.name from Product p")
    List<Object> listNames();


}
