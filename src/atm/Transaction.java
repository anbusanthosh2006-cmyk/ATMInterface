package atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static int transactionCounter = 10001;

    private String transactionId;
    private String type;
    private String details;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String type, String details, double amount) {

        this.transactionId = "TXN" + transactionCounter++;
        this.type = type;
        this.details = details;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return String.format(
                "%-9s | %-10s | %-35s | Rs. %-10.2f | %s",
                transactionId,
                type,
                details,
                amount,
                dateTime.format(formatter)
        );
    }
}
