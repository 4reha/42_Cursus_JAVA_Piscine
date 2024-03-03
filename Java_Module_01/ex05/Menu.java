
import java.util.Scanner;

public class Menu {

	enum Mode {
		PROD, DEV
	}

	Mode mode = Mode.PROD;
	TransactionsService transactionsService;

	public Menu() {
		transactionsService = new TransactionsService();
	}

	public void start(Mode mode) {
		this.mode = mode;

		displayMenu();
	}

	public void displayMenu() {

		while (true) {
			System.out.println("1. Add a user");
			System.out.println("2. View user balances");
			System.out.println("3. Perform a transfer");
			System.out.println("4. View all transactions for a specific user");
			if (mode == Mode.DEV) {
				System.out.println("5. DEV - remove a transfer by ID");
				System.out.println("6. DEV - check transfer validity");
				System.out.println("7. Finish execution");
			} else {
				System.out.println("5. Finish execution");
			}

			readOption();
			System.out.println("---------------------------------------------------------");
		}
	}

	public void readOption() {
		Scanner scanner = new Scanner(System.in);

		int option = -1;

		if (scanner.hasNextInt()) {
			option = scanner.nextInt();
			if ((this.mode == Mode.DEV && (option < 1 || option > 7))
					|| (this.mode == Mode.PROD && (option < 1 || option > 5))) {
				option = -1;
			}
		}

		switch (option) {
			case 1:
				addUser();
				break;
			case 2:
				viewUserBalances();
				break;
			case 3:
				performTransfer();
				break;
			case 4:
				viewAllTransactionsForUser();
				break;
			case 5:
				if (mode == Mode.DEV)
					removeTransferById();
				else
					System.exit(0);
				break;
			case 6:
				if (mode == Mode.DEV) {
					checkTransferValidity();
				}
				break;
			case 7:
				System.exit(0);
				break;
			default:
				System.err.println("Invalid option");
				break;
		}
	}

	public void addUser() {
		boolean valid = false;
		while (!valid) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter a user name and a balance");
				if (scanner.hasNextLine()) {
					String[] input = scanner.nextLine().trim().split("\\s+");
					if (input.length == 2) {
						String name = input[0];
						int balance = Integer.parseInt(input[1]);
						transactionsService.addUser(new User(name, balance));
						valid = true;
					}
				} else {
					System.err.println("-- Cancelled, return the menu ↩ ");
					return;
				}
				if (!valid)
					throw new IllegalArgumentException("Invalid input");
			} catch (NumberFormatException e) {
				System.err.println("Invalid balance");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

	};

	public void viewUserBalances() {
		boolean valid = false;
		while (!valid) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter a user ID");
				if (scanner.hasNextLine()) {
					String[] input = scanner.nextLine().trim().split("\\s+");
					if (input.length == 1) {
						Integer userId = Integer.parseInt(input[0]);
						User user = transactionsService.getUsersList().getUserById(userId);
						System.out.println(user.getName() + " - " + user.getBalance());

						valid = true;
					}
				} else {
					System.err.println("-- Cancelled, return the menu ↩ ");
					return;
				}
				if (!valid)
					throw new IllegalArgumentException("Invalid input");
			} catch (NumberFormatException e) {
				System.err.println("Invalid id");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

	};

	public void performTransfer() {
		boolean valid = false;
		while (!valid) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
				if (scanner.hasNextLine()) {
					String[] input = scanner.nextLine().trim().split("\\s+");
					if (input.length == 3) {
						Integer senderId = Integer.parseInt(input[0]);
						Integer recipientId = Integer.parseInt(input[1]);
						Integer transferAmount = Integer.parseInt(input[2]);
						if (transferAmount <= 0)
							throw new NumberFormatException("Invalid amount");
						transactionsService.performTransaction(senderId, recipientId, transferAmount);
						valid = true;
					}
				} else {
					System.err.println("-- Cancelled, return the menu ↩ ");
					return;
				}
				if (!valid)
					throw new IllegalArgumentException("Invalid input");
			} catch (NumberFormatException e) {
				System.err.println("Invalid id or amount");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	};

	public void viewAllTransactionsForUser() {
		boolean valid = false;
		while (!valid) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter a user ID");
				if (scanner.hasNextLine()) {
					String[] input = scanner.nextLine().trim().split("\\s+");
					if (input.length == 1) {
						Integer userId = Integer.parseInt(input[0]);
						Transaction[] transactions = transactionsService.getUserTransactions(userId);
						for (Transaction transaction : transactions) {
							String direction = transaction.getCategory().getText() == "INCOME" ? "From" : "To";
							System.out.println(
									String.format("%s %s(id = %d) %s%s with id = %s", direction,
											transaction.getRecipient().getName(), transaction.getRecipient().getId(),
											transaction.getCategory().getSign(), transaction.getAmount(), transaction.getId()));
						}
						valid = true;
					}
				} else {
					System.err.println("-- Cancelled, return the menu ↩ ");
					return;
				}
				if (!valid)
					throw new IllegalArgumentException("Invalid input");
			} catch (NumberFormatException e) {
				System.err.println("Invalid id");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	};

	public void removeTransferById() {
		boolean valid = false;
		while (!valid) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter a user ID and a transaction ID");
				if (scanner.hasNextLine()) {
					String[] input = scanner.nextLine().trim().split("\\s+");
					if (input.length == 2) {
						Integer userId = Integer.parseInt(input[0]);
						String transactionId = input[1];
						transactionsService.removeTransaction(userId, transactionId);
						valid = true;
					}
				} else {
					System.err.println("-- Cancelled, return the menu ↩ ");
					return;
				}
				if (!valid)
					throw new IllegalArgumentException("Invalid input");
			} catch (NumberFormatException e) {
				System.err.println("Invalid id");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	};

	public void checkTransferValidity() {
		try {
			Transaction[] transactions = transactionsService.getUnpairedTransactions();
			System.out.println("Check results:");
			for (Transaction transaction : transactions) {

				// must know which user has an unacknowledged transfer and from whom
				Transaction[] senderTrans = transactionsService.getUserTransactions(transaction.getSender().getId());
				User hasUnacknowledgedTransfer = null;
				User fromWhom = null;
				for (Transaction t : senderTrans) {
					if (t.getId().equals(transaction.getId())) {
						hasUnacknowledgedTransfer = transaction.getSender();
						fromWhom = transaction.getRecipient();
						break;
					}
				}
				if (hasUnacknowledgedTransfer == null) {
					Transaction[] recipientTrans = transactionsService.getUserTransactions(transaction.getRecipient().getId());
					for (Transaction t : recipientTrans) {
						if (t.getId().equals(transaction.getId())) {
							hasUnacknowledgedTransfer = transaction.getRecipient();
							fromWhom = transaction.getSender();
							break;
						}
					}
				}

				System.out.println(String.format("%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %d",
						hasUnacknowledgedTransfer.getName(), hasUnacknowledgedTransfer.getId(), transaction.getId(),
						fromWhom.getName(), fromWhom.getId(), transaction.getAmount()));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	};
}
