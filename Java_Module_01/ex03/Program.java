
public class Program {

  public static void main(String[] args) {

    try {
      UsersList usersList = new UsersArrayList();
      TransactionsList transactionsList = new TransactionsLinkedList();

      usersList.addUser(new User("John", 100));
      usersList.addUser(new User("Alex", 200));
      usersList.addUser(new User("Anna", 300));
      usersList.addUser(new User("Maria", 400));

      Transaction transaction1 = new Transaction(usersList.getUserById(1), usersList.getUserById(2), 50);
      Transaction transaction2 = new Transaction(usersList.getUserById(2), usersList.getUserById(3), 100);
      Transaction transaction3 = new Transaction(usersList.getUserById(3), usersList.getUserById(0), 150);

      transactionsList.addTransaction(transaction1);
      transactionsList.addTransaction(transaction2);
      transactionsList.addTransaction(transaction3);

      for (Transaction transaction : transactionsList.toArray()) {
        System.out.println(transaction);
      }

      transactionsList.removeTransactionById(transaction2.getId());
      System.err.println();

      for (Transaction transaction : transactionsList.toArray()) {
        System.out.println(transaction);
      }

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
