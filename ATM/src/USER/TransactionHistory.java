package USER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class TransactionHistory {
    private static List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(String accountNumber, String type,
                                    double amount, double balanceAfter) {
        transactions.add(new Transaction(accountNumber, type, amount, balanceAfter));
    }

    public static List<Transaction> getTransactions(String accountNumber) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }

    public static class Transaction {
        private String accountNumber;
        private String type;
        private double amount;
        private double balanceAfter;
        private LocalDateTime timestamp;

        public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.timestamp = LocalDateTime.now();
        }

        public String getAccountNumber() { return accountNumber; }
        public String getType() { return type; }
        public double getAmount() { return amount; }
        public double getBalanceAfter() { return balanceAfter; }
        public LocalDateTime getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.format("%s | %s | ₹%.2f | Balance: ₹%.2f",
                               timestamp.format(formatter), type, amount, balanceAfter);
        }
    }
}