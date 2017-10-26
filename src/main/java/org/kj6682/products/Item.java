package org.kj6682.products;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String category;

    private String producer;

    public Item(String name, String category, String producer) {
        this.name = name;
        this.category = category;
        this.producer = producer;
    }

    public Item() {
    }
}//:)
