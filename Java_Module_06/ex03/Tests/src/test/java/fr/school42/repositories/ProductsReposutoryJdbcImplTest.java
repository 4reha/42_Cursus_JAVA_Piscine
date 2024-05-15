package fr.school42.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import fr.school42.models.Product;

public class ProductsReposutoryJdbcImplTest {

  private ProductsRepository productsRepository;
  private DataSource dataSource;

  private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = List.of(
      new Product(0L, "Product 0", 9.99),
      new Product(1L, "Product 1", 19.99),
      new Product(2L, "Product 2", 14.99),
      new Product(3L, "Product 3", 24.99),
      new Product(4L, "Product 4", 29.99));

  private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Product 1", 19.99);
  private final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Product 1 Updated", 19.99);

  @BeforeEach
  public void init() {
    dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .addScripts("schema.sql", "data.sql")
        .build();

    productsRepository = new ProductsReposutoryJdbcImpl(new JdbcTemplate(dataSource));
  }

  @Test
  public void findAll() {
    List<Product> products = productsRepository.findAll();
    assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
  }

  @Test
  public void findById() {
    Product product = productsRepository.findById(1L).orElse(null);
    assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product);
  }

  @Test
  public void update() {
    productsRepository.update(new Product(1L, "Product 1 Updated", 19.99));
    Product product = productsRepository.findById(1L).orElse(null);
    assertEquals(EXPECTED_UPDATED_PRODUCT, product);
  }

  @Test
  public void save() {
    productsRepository.save(new Product("Product 6", 39.99));
    List<Product> products = productsRepository.findAll();
    assertEquals(6, products.size());
  }

  @Test
  public void delete() {
    productsRepository.delete(1L);
    List<Product> products = productsRepository.findAll();
    assertEquals(4, products.size());
  }

}
