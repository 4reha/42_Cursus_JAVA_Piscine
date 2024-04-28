package fr.school42.chat.models;

import java.util.ArrayList;
import java.util.List;

public class User {

	private Long id;
	private String login;
	private String password;
	private List<Chatroom> createdRooms;
	private List<Chatroom> chatrooms;

	public User() {
	}

	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createdRooms = new ArrayList<Chatroom>();
		this.chatrooms = new ArrayList<Chatroom>();
	}

	public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> chatrooms) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createdRooms = createdRooms;
		this.chatrooms = chatrooms;
	}

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

	public void addCreatedRoom(Chatroom chatroom) {
		for (Chatroom room : createdRooms) {
			if (room.getId() == chatroom.getId()) {
				return;
			}
		}
		createdRooms.add(chatroom);
	}

	public void setCreatedRooms(List<Chatroom> createdRooms) {
		this.createdRooms = createdRooms;
	}

	public List<Chatroom> getChatrooms() {
		return chatrooms;
	}

	public void addChatroom(Chatroom chatroom) {
		for (Chatroom room : chatrooms) {
			if (room.getId() == chatroom.getId()) {
				return;
			}
		}
		chatrooms.add(chatroom);
	}

	public void setChatrooms(List<Chatroom> chatrooms) {
		this.chatrooms = chatrooms;
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
		if (chatrooms != null ? !chatrooms.equals(user.chatrooms) : user.chatrooms != null)
			return false;
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (createdRooms != null ? createdRooms.hashCode() : 0);
		result = 31 * result + (chatrooms != null ? chatrooms.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "User : {\n" + "id=" + id + ",\n" + "login=\"" + login + "\",\n" + "password=\"" + password + "\",\n"
				+ "createdRooms=" + createdRooms + ",\n" + "chatrooms=" + chatrooms + "\n" + "}";
	}
}
