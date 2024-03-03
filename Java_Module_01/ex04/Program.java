
public class Program {

  public static void main(String[] args) {

    try {
      TransactionsService transactionsService = new TransactionsService();

      transactionsService.addUser(new User("user0", 100));
      transactionsService.addUser(new User("user1", 200));
      transactionsService.addUser(new User("user2", 300));

      transactionsService.performTransaction(0, 1, 50);

      System.out.println(transactionsService.getUserBalance(0)); // 50
      System.out.println(transactionsService.getUserBalance(1)); // 250

      transactionsService.performTransaction(1, 2, 100);

      System.out.println(transactionsService.getUserBalance(1)); // 150
      System.out.println(transactionsService.getUserBalance(2)); // 400

      for (Transaction transaction : transactionsService.getUserTransactions(0)) {
        System.out.println(transaction);
      }
      for (Transaction transaction : transactionsService.getUserTransactions(1)) {
        System.out.println(transaction);
      }
      for (Transaction transaction : transactionsService.getUserTransactions(2)) {
        System.out.println(transaction);
      }

      System.err.println();

      transactionsService.removeTransaction(1, transactionsService.getUserTransactions(1)[0].getId());

      for (Transaction transaction : transactionsService.getUnpairedTransactions()) {
        System.out.println(transaction);
      }

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
