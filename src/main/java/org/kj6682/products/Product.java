package org.kj6682.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.kj6682.commons.LocalDateDeserializer;
import org.kj6682.commons.LocalDateSerializer;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@Entity
class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String category;

    private short pieces;

    private String producer;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate created;

    private String status;

    protected Product() {
    }

    public Product(String name,
                   String category,
                   short pFactor,
                   String producer,
                   LocalDate since,
                   String status) {

        Assert.notNull(name, "an order needs a product");
        Assert.notNull(category, "an order needs a category");
        Assert.notNull(pieces, "an order needs a pFactor");
        Assert.notNull(producer, "an order needs a producer");
        Assert.notNull(since, "an order needs an origin date");
        Assert.notNull(status, "an order needs an state");

        this.name = name;
        this.category = category;
        this.pieces = pFactor;
        this.producer = producer;
        this.created = since;
        this.status = status;
    }

}// :)
