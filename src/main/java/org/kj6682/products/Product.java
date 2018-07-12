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
class Product {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String category;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    protected Product() {
    }

    public Product(String name,
                   String category,
                   LocalDate validityStartDate,
                   LocalDate validityEndDate) {

        Assert.notNull(name, "an order needs a product");
        Assert.notNull(category, "an order needs a category");

        this.name = name;
        this.category = category;
        this.startDate = validityStartDate;
        this.endDate = validityEndDate;

    }

}// :)
