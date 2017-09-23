package org.kj6682.i.products.attributes;

import lombok.Data;

@Data
public class Category {

    public Category(String name) {
        this.name = name;
    }

    private String name;

}
