package atm;

import java.util.ArrayList;

public class Account {

    private String accountId;
    private String pin;
    private double balance;
    private String accountType;

    private double dailyWithdrawalLimit = 20000.00;
    private double todayWithdrawn = 0.00;

    private ArrayList<Transaction> transactions;

    public Account(
            String accountId,
            String pin,
            double balance,
            String accountType) {

        this.accountId = accountId;
        this.pin = pin;
        this.balance = balance;
        this.accountType = accountType;

        transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public boolean changePin(String currentPin, String newPin) {

        if (!pin.equals(currentPin)) {
            return false;
        }

        if (newPin == null || !newPin.matches("\\d{4}")) {
            return false;
        }

        pin = newPin;
        return true;
    }

    public boolean withdraw(double amount) {

        if (amount <= 0) {
            return false;
        }

        if (amount > balance) {
            return false;
        }

        if (todayWithdrawn + amount > dailyWithdrawalLimit) {
            return false;
        }

        balance -= amount;
        todayWithdrawn += amount;

        transactions.add(
                new Transaction(
                        "WITHDRAW",
                        "Cash withdrawal",
                        amount
                )
        );

        return true;
    }

    public boolean deposit(double amount) {

        if (amount <= 0) {
            return false;
        }

        balance += amount;

        transactions.add(
                new Transaction(
                        "DEPOSIT",
                        "Cash deposit",
                        amount
                )
        );

        return true;
    }

    public boolean canWithdraw(double amount) {

        return amount > 0
                && amount <= balance
                && todayWithdrawn + amount <= dailyWithdrawalLimit;
    }

    public boolean hasEnoughBalance(double amount) {

        return amount > 0 && amount <= balance;
    }

    public boolean hasReachedDailyLimit(double amount) {

        return todayWithdrawn + amount > dailyWithdrawalLimit;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void addTransaction(
            String type,
            String details,
            double amount) {

        transactions.add(
                new Transaction(
                        type,
                        details,
                        amount
                )
        );
    }

    public void showTransactionHistory() {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                    TRANSACTION HISTORY");
        System.out.println("==============================================================");

        if (transactions.isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            System.out.println(
                    "ID        | TYPE       | DETAILS                             | AMOUNT       | DATE/TIME"
            );

            System.out.println("--------------------------------------------------------------");

            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }

        System.out.println("==============================================================");
    }

    public void showAccountDetails() {

        System.out.println();
        System.out.println("==================================");
        System.out.println("          ACCOUNT DETAILS");
        System.out.println("==================================");
        System.out.println("Account ID       : " + accountId);
        System.out.println("Account Type     : " + accountType);
        System.out.printf(
                "Available Balance: Rs. %.2f%n",
                balance
        );
        System.out.printf(
                "Daily Withdrawal : Rs. %.2f%n",
                dailyWithdrawalLimit
        );
        System.out.printf(
                "Withdrawn Today  : Rs. %.2f%n",
                todayWithdrawn
        );
        System.out.println("==================================");
    }
}