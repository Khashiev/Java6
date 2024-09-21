package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(1L, "apple", 100D),
            new Product(2L, "orange", 150D),
            new Product(3L, "milk", 120D),
            new Product(4L, "cola", 80D),
            new Product(5L, "lays", 200D)
    ));

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "apple", 100D);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "potato", 50D);
    final Product EXPECTED_SAVE_PRODUCT = new Product(6L, "banana", 300D);

//    private DataSource dataSource;
    ProductsRepository productsRepository;
    EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsRepository = new ProductsRepositoryJdbcImpl(embeddedDatabase);
    }

    @Test
    void findAllTest() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void findByIdTest() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(1L).get());
    }

    @Test
    void updateTest() throws SQLException {
        productsRepository.update(new Product(2L, "potato", 50D));
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(2L).get());
    }

    @Test
    void saveTest() throws SQLException {
        productsRepository.save(new Product(6L, "banana", 300D));
        Assertions.assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(6L).get());
    }

    @Test
    void deleteTest() throws SQLException {
        productsRepository.delete(3L);
        Assertions.assertEquals(Optional.empty(), productsRepository.findById(3L));
    }
}
