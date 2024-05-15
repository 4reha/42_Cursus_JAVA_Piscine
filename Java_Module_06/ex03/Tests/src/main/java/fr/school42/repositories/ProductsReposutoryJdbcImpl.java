package fr.school42.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;

import fr.school42.models.Product;

public class ProductsReposutoryJdbcImpl implements ProductsRepository {

  private final JdbcTemplate jdbcTemplate;

  public class ProductRowMapper implements org.springframework.jdbc.core.RowMapper<Product> {
    @Override
    public Product mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
      return new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"));
    }

  }

  public ProductsReposutoryJdbcImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Product> findAll() {
    return jdbcTemplate.query("SELECT * FROM products", new ProductRowMapper());
  }

  @Override
  public Optional<Product> findById(Long id) {
    return jdbcTemplate.query("SELECT * FROM products WHERE id = ?", new ProductRowMapper(), id)
        .stream()
        .findAny();
  }

  @Override
  public void update(Product product) {
    jdbcTemplate.update("UPDATE products SET name = ?, price = ? WHERE id = ?",
        product.getName(), product.getPrice(), product.getId());
  }

  @Override
  public void save(Product product) {
    jdbcTemplate.update("INSERT INTO products (name, price) VALUES (?, ?)",
        product.getName(), product.getPrice());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
  }

}
