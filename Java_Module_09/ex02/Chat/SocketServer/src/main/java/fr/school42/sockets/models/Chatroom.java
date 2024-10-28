package fr.school42.sockets.models;

public class Chatroom {

  private Long id;
  private String name;

  public Chatroom() {
  }

  public Chatroom(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Chatroom(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Chatroom{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Chatroom chatroom = (Chatroom) o;

    if (id != null ? !id.equals(chatroom.id) : chatroom.id != null)
      return false;
    return name != null ? name.equals(chatroom.name) : chatroom.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}