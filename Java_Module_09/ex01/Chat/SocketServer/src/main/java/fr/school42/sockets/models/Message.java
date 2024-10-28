package fr.school42.sockets.models;

import java.util.Date;

public class Message {

  private Long id;
  private User sender;
  private String text;
  private Date sentAt;

  public Message() {
  }

  public Message(Long id, User sender, String text, Date sentAt) {
    this.id = id;
    this.sender = sender;
    this.text = text;
    this.sentAt = sentAt;
  }

  public Message(User sender, String text) {
    this.sender = sender;
    this.text = text;
    this.sentAt = new Date();
  }

  public Message(User sender, String text, Date sentAt) {
    this.sender = sender;
    this.text = text;
    this.sentAt = sentAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getSentAt() {
    return sentAt;
  }

  public void setSentAt(Date sentAt) {
    this.sentAt = sentAt;
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", sender=" + sender +
        ", text='" + text + '\'' +
        ", sentAt=" + sentAt +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Message message = (Message) o;

    if (id != null ? !id.equals(message.id) : message.id != null)
      return false;
    if (sender != null ? !sender.equals(message.sender) : message.sender != null)
      return false;
    if (text != null ? !text.equals(message.text) : message.text != null)
      return false;
    return sentAt != null ? sentAt.equals(message.sentAt) : message.sentAt == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (sender != null ? sender.hashCode() : 0);
    result = 31 * result + (text != null ? text.hashCode() : 0);
    result = 31 * result + (sentAt != null ? sentAt.hashCode() : 0);
    return result;
  }

}
