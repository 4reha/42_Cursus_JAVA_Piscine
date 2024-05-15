package fr.school42.models;

public class Product {

  private Long id;
  private String name;
  private Double price;

  public Product() {
  }

  public Product(Long id, String name, Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Product))
      return false;

    Product product = (Product) o;

    if (id != null ? !id.equals(product.id) : product.id != null)
      return false;
    if (name != null ? !name.equals(product.name) : product.name != null)
      return false;
    return price != null ? price.equals(product.price) : product.price == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        '}';
  }

}
