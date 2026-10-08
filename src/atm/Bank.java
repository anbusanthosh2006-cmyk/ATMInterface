package atm;

import java.util.HashMap;

public class Bank {

    private HashMap<String, Account> accounts;

    public Bank() {

        accounts = new HashMap<>();

        accounts.put(
                "1001",
                new Account(
                        "1001",
                        "1234",
                        10000.00,
                        "Savings"
                )
        );

        accounts.put(
                "1002",
                new Account(
                        "1002",
                        "2222",
                        5000.00,
                        "Savings"
                )
        );

        accounts.put(
                "1003",
                new Account(
                        "1003",
                        "3333",
                        7500.00,
                        "Current"
                )
        );
    }

    public Account getAccount(String accountId) {

        return accounts.get(accountId);
    }

    public boolean transfer(
            Account sender,
            String receiverId,
            double amount) {

        Account receiver = accounts.get(receiverId);

        if (receiver == null) {

            System.out.println("Recipient account not found.");
            return false;
        }

        if (sender.getAccountId().equals(receiverId)) {

            System.out.println(
                    "Cannot transfer money to your own account."
            );

            return false;
        }

        if (amount <= 0) {

            System.out.println("Invalid transfer amount.");
            return false;
        }

        if (!sender.hasEnoughBalance(amount)) {

            System.out.println("Insufficient Funds");
            return false;
        }

        // Transfer money
        sender.deductBalance(amount);
        receiver.addBalance(amount);

        // Sender history
        sender.addTransaction(
                "TRANSFER",
                "Transferred to Account " + receiverId,
                amount
        );

        // Receiver history
        receiver.addTransaction(
                "TRANSFER",
                "Received from Account "
                        + sender.getAccountId(),
                amount
        );

        return true;
    }
}
