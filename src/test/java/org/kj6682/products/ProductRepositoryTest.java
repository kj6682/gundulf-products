package org.kj6682.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(profiles = {"h2"})
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repo;

    @Test
    public void insertOneProduct() {
        // given
        Product simple = new Product("product", "category",
                LocalDate.of(2017, 12, 3),
                LocalDate.of(2017, 12, 4));
        repo.save(simple);
        Product other = repo.findOne(1L);

        // then
        assertThat(other != null);
     }

    @Test
    public void updateOneProduct() {
        // given
        Product one = new Product("product", "category",
                LocalDate.of(2017, 12, 3),
                LocalDate.of(2017, 12, 4));
        repo.save(one);

        Product two = repo.findOne(1L);
        assertThat(two != null);
        assertThat(two.equals(one));

        // then
        two.setEndDate(LocalDate.MAX);
        repo.save(two);

        Product three = repo.findOne(1L);
        assertThat(three != null);
        assertThat(!one.equals(three) );
        assertThat(three.getEndDate().equals(LocalDate.MAX));

    }

    @Test
    public void deleteOneProduct() {
        // given
        Product one = new Product("product", "category",
                LocalDate.of(2017, 12, 3),
                LocalDate.of(2017, 12, 4));
        repo.save(one);

        Product two = repo.findOne(1L);
        assertThat(two != null);
        assertThat(two.equals(one));

        //when
        repo.delete(1L);

        //then
        Product three = repo.findOne(1L);
        assertThat(three == null);

    }


}//:)



