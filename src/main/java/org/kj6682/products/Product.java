package org.kj6682.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.kj6682.commons.LocalDateDeserializer;
import org.kj6682.commons.LocalDateSerializer;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@IdClass(ProductKey.class)
class Product {


    @Id
    private String name;
    @Id
    private Integer pieces;

    private String producer;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    protected Product() {
    }

    public Product(String name,
                   Integer pieces,
                   String producer,
                   LocalDate validityStartDate,
                   LocalDate validityEndDate) {

        Assert.notNull(name, "an order needs a product");
        Assert.notNull(pieces, "an order needs a pFactor");
        Assert.notNull(producer, "an order needs a producer");

        this.name = name;
        this.pieces = pieces;
        this.producer = producer;
        this.startDate = validityStartDate;
        this.endDate = validityEndDate;

    }

}// :)
