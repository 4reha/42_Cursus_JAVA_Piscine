
public interface TransactionsList {

	void addTransaction(Transaction transaction) throws NullPointerException;

	void removeTransactionById(String identifier) throws TransactionsLinkedList.TransactionNotFoundException;

	Transaction[] toArray();
}