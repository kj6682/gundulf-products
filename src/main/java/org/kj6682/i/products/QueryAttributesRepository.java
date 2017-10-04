package org.kj6682.i.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueryAttributesRepository extends JpaRepository<Product, Long> {

    @Query("select DISTINCT p.name from Product p")
    List<Object> listNames();

    @Query("select DISTINCT p.category from Product p")
    List<Object> listCategories();

    @Query("select DISTINCT p.category, p.name from Product p")
    List<Object[]> listCategoriesAndNames();
}
