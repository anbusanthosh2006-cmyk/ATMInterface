package atm;

import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner scanner;

    public ATM(Bank bank) {

        this.bank = bank;
        scanner = new Scanner(System.in);
    }

    public void start() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          WELCOME TO SMART ATM");
        System.out.println("========================================");

        Account account = authenticate();

        if (account == null) {

            System.out.println();
            System.out.println("ACCESS DENIED");
            System.out.println("Thank you for using Smart ATM.");

            return;
        }

        showMenu(account);
    }

    private Account authenticate() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account account = bank.getAccount(userId);

            if (account != null
                    && account.verifyPin(pin)) {

                System.out.println();
                System.out.println("Login Successful!");
                System.out.println(
                        "Welcome, User " + userId
                );

                return account;
            }

            attempts++;

            System.out.println();
            System.out.println("Invalid User ID or PIN.");
            System.out.println(
                    "Attempts remaining: "
                    + (3 - attempts)
            );
            System.out.println();
        }

        System.out.println(
                "Maximum login attempts exceeded."
        );

        return null;
    }

    private void showMenu(Account account) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==================================");
            System.out.println("            SMART ATM");
            System.out.println("==================================");
            System.out.println("1. Balance Enquiry");
            System.out.println("2. Transaction History");
            System.out.println("3. Withdraw");
            System.out.println("4. Deposit");
            System.out.println("5. Transfer");
            System.out.println("6. Change PIN");
            System.out.println("7. Account Details");
            System.out.println("8. Quit");
            System.out.println("==================================");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    balanceEnquiry(account);
                    break;

                case "2":
                    account.showTransactionHistory();
                    break;

                case "3":
                    withdraw(account);
                    break;

                case "4":
                    deposit(account);
                    break;

                case "5":
                    transfer(account);
                    break;

                case "6":
                    changePin(account);
                    break;

                case "7":
                    account.showAccountDetails();
                    break;

                case "8":

                    System.out.println();
                    System.out.println(
                            "Thank you for using Smart ATM!"
                    );
                    System.out.println(
                            "Have a great day!"
                    );

                    running = false;
                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private void balanceEnquiry(Account account) {

        System.out.println();
        System.out.println("========== BALANCE ==========");

        System.out.printf(
                "Available Balance: Rs. %.2f%n",
                account.getBalance()
        );

        System.out.println("=============================");
    }

    private void withdraw(Account account) {

        System.out.print(
                "Enter withdrawal amount: "
        );

        try {

            double amount =
                    Double.parseDouble(
                            scanner.nextLine()
                    );

            if (amount <= 0) {

                System.out.println(
                        "Invalid amount."
                );

                return;
            }

            if (!account.hasEnoughBalance(amount)) {

                System.out.println(
                        "Insufficient Funds"
                );

                return;
            }

            if (account.hasReachedDailyLimit(amount)) {

                System.out.println(
                        "Daily withdrawal limit exceeded."
                );

                return;
            }

            account.withdraw(amount);

            System.out.println();
            System.out.println(
                    "Withdrawal Successful!"
            );

            System.out.printf(
                    "Withdrawn Amount : Rs. %.2f%n",
                    amount
            );

            System.out.printf(
                    "Remaining Balance: Rs. %.2f%n",
                    account.getBalance()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number."
            );
        }
    }

    private void deposit(Account account) {

        System.out.print(
                "Enter deposit amount: "
        );

        try {

            double amount =
                    Double.parseDouble(
                            scanner.nextLine()
                    );

            if (amount <= 0) {

                System.out.println(
                        "Invalid amount."
                );

                return;
            }

            account.deposit(amount);

            System.out.println();
            System.out.println(
                    "Deposit Successful!"
            );

            System.out.printf(
                    "Deposited Amount: Rs. %.2f%n",
                    amount
            );

            System.out.printf(
                    "New Balance: Rs. %.2f%n",
                    account.getBalance()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number."
            );
        }
    }

    private void transfer(Account account) {

        System.out.print(
                "Enter recipient Account ID: "
        );

        String receiverId =
                scanner.nextLine();

        System.out.print(
                "Enter transfer amount: "
        );

        try {

            double amount =
                    Double.parseDouble(
                            scanner.nextLine()
                    );

            boolean success =
                    bank.transfer(
                            account,
                            receiverId,
                            amount
                    );

            if (success) {

                System.out.println();
                System.out.println(
                        "Transfer Successful!"
                );

                System.out.println(
                        "Recipient Account: "
                        + receiverId
                );

                System.out.printf(
                        "Transferred Amount: Rs. %.2f%n",
                        amount
                );

                System.out.printf(
                        "Remaining Balance: Rs. %.2f%n",
                        account.getBalance()
                );
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Please enter a valid number."
            );
        }
    }

    private void changePin(Account account) {

        System.out.println();
        System.out.println("========== CHANGE PIN ==========");

        System.out.print(
                "Enter current PIN: "
        );

        String currentPin =
                scanner.nextLine();

        System.out.print(
                "Enter new 4-digit PIN: "
        );

        String newPin =
                scanner.nextLine();

        System.out.print(
                "Confirm new PIN: "
        );

        String confirmPin =
                scanner.nextLine();

        if (!newPin.equals(confirmPin)) {

            System.out.println(
                    "New PIN and confirmation do not match."
            );

            return;
        }

        if (account.changePin(
                currentPin,
                newPin)) {

            System.out.println(
                    "PIN changed successfully!"
            );

        } else {

            System.out.println(
                    "Invalid current PIN or new PIN."
            );
        }
    }
}
