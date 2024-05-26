package school42.spring.service.models;

public class User {
  private Long id;
  private String email;

  public User() {
  }

  public User(String email) {
    this.email = email;
  }

  public User(Long id, String email) {
    this.id = id;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        '}';
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
    return email != null ? email.equals(user.email) : user.email == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

}
