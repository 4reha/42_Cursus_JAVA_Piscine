
import java.util.UUID;

public class Transaction {

	public enum TransferCategory {
		CREDIT("OUTCOME", ""),
		DEBIT("INCOME", "+");

		private final String TEXT;
		private final String SIGN;

		TransferCategory(String text, String sign) {
			this.TEXT = text;
			this.SIGN = sign;
		}

		public String getText() {
			return TEXT;
		}

		public String getSign() {
			return SIGN;
		}
	}

	private String identifier;
	private User recipient;
	private User sender;
	private TransferCategory category;
	private Integer amount;

	public Transaction(User recipient, User sender, Integer amount) {
		this.identifier = UUID.randomUUID().toString();
		this.recipient = recipient;
		this.sender = sender;
		if (amount > 0) {
			setCategory(TransferCategory.DEBIT);
		} else {
			setCategory(TransferCategory.CREDIT);
		}
		this.amount = amount;
	}

	public String getId() {
		return identifier;
	}

	public User getRecipient() {
		return recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setCategory(TransferCategory transferCategory) {
		this.category = transferCategory;
	}

	public TransferCategory getCategory() {
		return category;
	}

	public Integer getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return (String.format("%s -> %s, %s%s, %s, %s", sender.getName(), recipient.getName(), category.getSign(),
				amount, category.getText(), identifier));
	}
}
