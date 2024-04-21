package fr.school42.chat.models;

import java.util.List;

public class User {

	private Long id;
	private String login;
	private String password;
	private List<Chatroom> createdRooms;
	private List<Chatroom> chatRooms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Chatroom> getCreatedRooms() {
		return createdRooms;
	}

	public void setCreatedRooms(List<Chatroom> createdRooms) {
		this.createdRooms = createdRooms;
	}

	public List<Chatroom> getChatRooms() {
		return chatRooms;
	}

	public void setChatRooms(List<Chatroom> chatRooms) {
		this.chatRooms = chatRooms;
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
		if (createdRooms != null ? !createdRooms.equals(user.createdRooms) : user.createdRooms != null)
			return false;
		if (chatRooms != null ? !chatRooms.equals(user.chatRooms) : user.chatRooms != null)
			return false;
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (createdRooms != null ? createdRooms.hashCode() : 0);
		result = 31 * result + (chatRooms != null ? chatRooms.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", createdRooms="
				+ createdRooms + ", chatRooms=" + chatRooms + '}';
	}
}
