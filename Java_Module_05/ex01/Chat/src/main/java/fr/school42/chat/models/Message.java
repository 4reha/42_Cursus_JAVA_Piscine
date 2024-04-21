package fr.school42.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private Long id;
	private User author;
	private Chatroom chatroom;
	private String text;
	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDateTime() {
		return createdAt;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.createdAt = dateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Message))
			return false;

		Message message = (Message) o;

		if (id != null ? !id.equals(message.id) : message.id != null)
			return false;
		if (author != null ? !author.equals(message.author) : message.author != null)
			return false;
		if (text != null ? !text.equals(message.text) : message.text != null)
			return false;
		if (createdAt != null ? !createdAt.equals(message.createdAt) : message.createdAt != null)
			return false;
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "Message : {\n" +
				"id=" + id + ",\n" +
				"author=" + author + ",\n" +
				"room=" + chatroom + ",\n" +
				"text=" + text + ",\n" +
				"dateTime=" + createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
				+ "\n" +
				"}";
	}
}
