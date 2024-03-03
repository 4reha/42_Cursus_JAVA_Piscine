public interface UsersList {

	public void addUser(User user) throws NullPointerException;

	public User getUserById(Integer id) throws UsersArrayList.UserNotFoundException;

	public User getUserByIndex(Integer index) throws UsersArrayList.UserNotFoundException, ArrayIndexOutOfBoundsException;

	public Integer getUsersCount();

}
