
public class Program {

  public static void main(String[] args) {

    try {
      UsersList usersList = new UsersArrayList();

      usersList.addUser(new User("John", 100));
      usersList.addUser(new User("Alex", 200));
      usersList.addUser(new User("Anna", 300));
      usersList.addUser(new User("Maria", 400));

      System.out.println("Users count: " + usersList.getUsersCount());
      System.out.println("User with id 2: " + usersList.getUserById(2));
      System.out.println("User with index 3: " + usersList.getUserByIndex(3));

      System.err.println("User with id 5: " + usersList.getUserById(5));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
