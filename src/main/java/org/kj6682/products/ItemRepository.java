package org.kj6682.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    List<Item> findByNameContainingIgnoreCaseOrderByName(@Param("name") String name);

    List<Item> findByNameStartsWith(@Param("name") String name);

    List<Item> findByCategory(@Param("category") String category);

    List<Item> findByProducer(@Param("producer") String producer);

    List<Item> findAllByOrderByName();

    Page<Item> findAll(Pageable pageable);

    void delete(Long id);

}
