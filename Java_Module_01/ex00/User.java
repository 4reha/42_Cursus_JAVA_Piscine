
public class User {

	private static Integer lastId = 0;
	private Integer id;
	private String name;
	private Integer balance;

	public User(String name, Integer balance) {
		this.id = lastId++;
		this.name = name;
		setBalance(balance);
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

	@Override
	public String toString() {
		return (String.format("[%s] %s Balance=%s", id, name, balance));
	}
}