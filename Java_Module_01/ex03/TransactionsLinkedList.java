
public class TransactionsLinkedList implements TransactionsList {

	private class Node {
		private Transaction transaction;
		private Node next;
		private Node prev;

		public Node(Transaction transaction) {
			this.transaction = transaction;
			this.next = null;
			this.prev = null;
		}

		public Transaction getTransaction() {
			return transaction;
		}

		public Node getNext() {
			return next;
		}

		public Node getPrev() {
			return prev;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}

	}

	private Node head;
	private Node tail;
	private Integer size;

	public TransactionsLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		if (transaction == null) {
			throw new NullPointerException();
		}
		Node newNode = new Node(transaction);
		if (head == null) {
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail = newNode;
		}
		size++;
	}

	@Override
	public void removeTransactionById(String identifier) throws TransactionNotFoundException {
		Node current = head;
		while (current != null) {
			if (current.getTransaction().getId().equals(identifier)) {
				if (current.getPrev() != null) {
					current.getPrev().setNext(current.getNext());
				} else {
					head = current.getNext();
				}
				if (current.getNext() != null) {
					current.getNext().setPrev(current.getPrev());
				} else {
					tail = current.getPrev();
				}
				size--;
				return;
			}
			current = current.getNext();
		}
		throw new TransactionNotFoundException();
	}

	@Override
	public Transaction[] toArray() {
		Transaction[] transactions = new Transaction[size];
		Node current = head;
		for (int i = 0; i < size; i++) {
			transactions[i] = current.getTransaction();
			current = current.getNext();
		}
		return transactions;
	}

	public static class TransactionNotFoundException extends RuntimeException {
		public TransactionNotFoundException() {
			super("Transaction not found");
		}
	}

}