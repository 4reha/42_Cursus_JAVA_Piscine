package fr.school42.classes;

public class Car {
  private String model;
  private Integer year;
  private Double price;
  private Boolean isUsed;
  private Long mileage; // in km

  public Car() {
    this.model = "Default model";
    this.year = 0;
    this.price = 0.0;
    this.isUsed = false;
    this.mileage = 0L;
  }

  public Car(String model, Integer year, Double price, Boolean isUsed, Long mileage) {
    this.model = model;
    this.year = year;
    this.price = price;
    this.isUsed = isUsed;
    this.mileage = mileage;
  }

  public Long drive(Double hours, Double speed) {
    // distance = time * speed (in km)
    Long distance = Math.round(hours * speed);
    this.mileage += distance;
    return distance;
  }

  public void risePrice(Double percentage) {
    this.price += this.price * percentage / 100;
  }

  @Override
  public String toString() {
    return "Car{" +
        "model='" + model + '\'' +
        ", year=" + year +
        ", price=" + price +
        ", isUsed=" + isUsed +
        ", mileage=" + mileage +
        '}';
  }
}