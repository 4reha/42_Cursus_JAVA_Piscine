
public class Program {

  public static void main(String[] args) {

    User user1 = new User("User1", 100);
    User user2 = new User("User2", 200);

    System.out.println(user1);
    System.out.println(user2);

    Transaction transaction1 = new Transaction(user1, user2, 50);
    Transaction transaction2 = new Transaction(user2, user1, -100);

    System.out.println(transaction1);
    System.out.println(transaction2);
  }
}
