
import java.util.UUID;

public class TransactionsService {

	private UsersList usersList;
	private TransactionsLinkedList unpairedTransactions;

	public TransactionsService() {
		usersList = new UsersArrayList();
		unpairedTransactions = new TransactionsLinkedList();
	}

	public UsersList getUsersList() {
		return usersList;
	}

	public void addUser(User user) throws NullPointerException {
		usersList.addUser(user);
	}

	public Integer getUserBalance(Integer userId) throws UsersArrayList.UserNotFoundException {
		return usersList.getUserById(userId).getBalance();
	}

	public void performTransaction(Integer senderId, Integer recipientId, Integer transferAmount)
			throws UsersArrayList.UserNotFoundException, IllegalTransactionException {

		User sender = this.usersList.getUserById(senderId);
		User recipient = this.usersList.getUserById(recipientId);

		if (senderId.equals(recipientId)) {
			throw new IllegalTransactionException("Sender and recipient are the same user");
		}

		if (transferAmount > 0 && transferAmount > sender.getBalance()) {
			throw new IllegalTransactionException("Insufficient funds");
		} else if (transferAmount < 0 && -transferAmount > recipient.getBalance()) {
			throw new IllegalTransactionException("Insufficient funds");
		}

		String transactionId = UUID.randomUUID().toString();

		Transaction trans1 = new Transaction(sender, recipient, transferAmount, transactionId);
		Transaction trans2 = new Transaction(recipient, sender, -transferAmount, transactionId);

		sender.setBalance(sender.getBalance() - transferAmount);
		recipient.setBalance(recipient.getBalance() + transferAmount);

		sender.getTransactionsList().addTransaction(trans2);
		recipient.getTransactionsList().addTransaction(trans1);
	};

	public Transaction[] getUserTransactions(Integer userId) throws UsersArrayList.UserNotFoundException {
		User user = usersList.getUserById(userId);
		TransactionsList userTransactions = user.getTransactionsList();
		return userTransactions.toArray();
	}

	public void removeTransaction(Integer userId, String transactionId)
			throws UsersArrayList.UserNotFoundException, TransactionsLinkedList.TransactionNotFoundException {
		User firstUser = usersList.getUserById(userId);
		Transaction toDelete = ((TransactionsLinkedList) firstUser.getTransactionsList()).getTransactionById(transactionId);

		try {
			this.unpairedTransactions.getTransactionById(transactionId);
			this.unpairedTransactions.removeTransactionById(transactionId);
		} catch (Exception e) {
			User secondUser = (userId == toDelete.getRecipient().getId())
					? usersList.getUserById(toDelete.getSender().getId())
					: usersList.getUserById(toDelete.getRecipient().getId());
			Transaction toSave = ((TransactionsLinkedList) secondUser.getTransactionsList())
					.getTransactionById(transactionId);
			this.unpairedTransactions.addTransaction(toSave);
		}
		firstUser.getTransactionsList().removeTransactionById(transactionId);
	}

	public Transaction[] getUnpairedTransactions() {
		return unpairedTransactions.toArray();
	}

	public static class IllegalTransactionException extends RuntimeException {
		public IllegalTransactionException(String message) {
			super("Illegal transaction: " + message);
		}
	}

}
