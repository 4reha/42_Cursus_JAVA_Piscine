
public class User {

	private Integer id;
	private String name;
	private Integer balance;
	private TransactionsList transactionsList;

	public User(String name, Integer balance) {
		this.id = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		setBalance(balance);
		this.transactionsList = new TransactionsLinkedList();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		if (balance < 0)
			balance = 0;
		this.balance = balance;
	}

	public TransactionsList getTransactionsList() {
		return transactionsList;
	}

	@Override
	public String toString() {
		return (String.format("[%s] %s Balance=%s", id, name, balance));
	}
}