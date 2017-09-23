package org.kj6682.i.products.attributes;

import lombok.Data;

@Data
public class CategoryName {

    private String category;
    private String name;

    public CategoryName(String category, String name) {
        this.category = category;
        this.name = name;
    }
}
