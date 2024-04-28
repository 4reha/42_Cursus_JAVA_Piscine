package fr.school42.chat.models;

import java.util.List;

public class Chatroom {

	private Long id;
	private String name;
	private User owner;
	private List<Message> messages;

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Chatroom))
			return false;

		Chatroom chatroom = (Chatroom) o;

		if (id != null ? !id.equals(chatroom.id) : chatroom.id != null)
			return false;
		if (name != null ? !name.equals(chatroom.name) : chatroom.name != null)
			return false;
		if (owner != null ? !owner.equals(chatroom.owner) : chatroom.owner != null)
			return false;
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", owner=" + owner +
				'}';
	}
}
