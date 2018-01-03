package org.kj6682.products;

import java.io.Serializable;

class ProductKey implements Serializable{

    private String name;
    private Integer pieces;

    public ProductKey(){}

    public ProductKey(String name, Integer pieces) {
        this.name = name;
        this.pieces = pieces;
    }

}
