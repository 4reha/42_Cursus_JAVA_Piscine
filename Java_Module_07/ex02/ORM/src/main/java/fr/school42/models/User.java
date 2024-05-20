package fr.school42.models;

import fr.school42.annotations.*;

@OrmEntity(table = "simple_user")
public class User {
  @OrmColumnId
  private Long id;

  @OrmColumn(name = "first_name", length = 10)
  private String firstName;

  @OrmColumn(name = "last_name", length = 10)
  private String lastName;

  @OrmColumn(name = "age")
  private Integer age;

  public User() {
  }

  public User(String firstName, String lastName, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    User user = (User) o;

    if (id != null ? !id.equals(user.id) : user.id != null)
      return false;
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null)
      return false;
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
      return false;
    return age != null ? age.equals(user.age) : user.age == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return """
        User{
          id=%d,
          firstName=%s,
          lastName=%s,
          age=%d
        }""".formatted(id, firstName, lastName, age);
  }

}