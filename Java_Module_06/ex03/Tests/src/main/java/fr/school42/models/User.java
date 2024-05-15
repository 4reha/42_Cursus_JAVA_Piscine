package fr.school42.models;

public class User {
  private Long id;
  private String login;
  private String password;
  private boolean isAuthenticated;

  public User() {
  }

  public User(Long id, String login, String password, boolean isAuthenticated) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.isAuthenticated = isAuthenticated;
  }

  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAuthenticated(boolean isAuthenticated) {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;

    User user = (User) o;

    if (id != null ? !id.equals(user.id) : user.id != null)
      return false;
    if (login != null ? !login.equals(user.login) : user.login != null)
      return false;
    if (password != null ? !password.equals(user.password) : user.password != null)
      return false;
    return isAuthenticated == user.isAuthenticated;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (isAuthenticated ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", isAuthenticated=" + isAuthenticated +
        '}';
  }

}
