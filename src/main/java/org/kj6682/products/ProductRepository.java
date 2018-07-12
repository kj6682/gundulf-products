package org.kj6682.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findByCategoryOrderByName(@Param("category") String category);

    List<Product> findByCategoryOrderByName(@Param("category") String category, Pageable pageable);

    List<Product> findByNameContainingIgnoreCaseOrderByName(@Param("name") String name);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    void delete(Long id);

}
