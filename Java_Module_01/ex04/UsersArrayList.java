public class UsersArrayList implements UsersList {

	User[] users;
	Integer size;

	public UsersArrayList() {
		users = new User[10];
		size = 0;
	}

	@Override
	public void addUser(User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		if (size == users.length) {
			User[] newUsers = new User[users.length + users.length / 2];
			for (int i = 0; i < users.length; i++) {
				newUsers[i] = users[i];
			}
			users = newUsers;
		}
		users[size++] = user;
	}

	@Override
	public User getUserById(Integer id) throws UserNotFoundException {
		for (int i = 0; i < size; i++) {
			if (users[i].getId().equals(id)) {
				return users[i];
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	public User getUserByIndex(Integer index) throws UserNotFoundException, ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= users.length) {
			throw new ArrayIndexOutOfBoundsException();
		} else if (users[index] == null) {
			throw new UserNotFoundException();
		}
		return users[index];
	}

	@Override
	public Integer getUsersCount() {
		return size;
	}

	public static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException() {
			super("User not found!");
		}
	}
}
