package USER;

import java.util.Scanner;
import java.util.List;

public class user_main {
    private Scanner sc;
    private account currentAccount;

    public user_main() {
        this.sc = new Scanner(System.in);
    }

    public void runUserSession() {
        System.out.println("=======================================");
        System.out.println("    WELCOME TO SMART ATM SIMULATOR");
        System.out.println("=======================================");
        System.out.println();
        System.out.println("Secure • Reliable • Simulated Banking");
        System.out.println();

        if (authenticateUser()) {
            showATMMenu();
        } else {
            System.out.println("Authentication failed. Returning to main menu...");
        }
    }

    private boolean authenticateUser() {
        System.out.println("Please insert your virtual card to begin...");
        System.out.print("Enter your Card Number (16 digits): ");
        String cardNumber = sc.next();

        account account = account_manager.findAccByCard(cardNumber);
        if (account == null) {
            System.out.println("Invalid card number. Card not found.");
            return false;
        }

        System.out.println("Card Number [" + cardNumber + "] accepted. Proceeding...");

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter your PIN: ");
            String pin = sc.next();

            if (account_manager.authenticate(cardNumber, pin)) {
                this.currentAccount = account;
                System.out.println("Authentication successful!");
                System.out.println("Welcome, " + account.getHolderName() + "!");
                return true;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts remaining: " + (3 - attempts));
            }
        }

        System.out.println("Maximum attempts exceeded. Card blocked for security.");
        return false;
    }

    private void showATMMenu() {
        while (true) {
            System.out.println("\n=======================================");
            System.out.println("           ATM MAIN MENU");
            System.out.println("=======================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Transfer Money");
            System.out.println("5. Change PIN");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdrawCash();
                    break;
                case 3:
                    depositCash();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    changePIN();
                    break;
                case 6:
                    showTransactionHistory();
                    break;
                case 7:
                    System.out.println("Thank you for using our ATM services!");
                    System.out.println("Please take your card and receipt.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void checkBalance() {
        System.out.println("\n=======================================");
        System.out.println("          BALANCE INQUIRY");
        System.out.println("=======================================");
        System.out.println("Account Number: " + currentAccount.getAccountNumber());
        System.out.println("Account Holder: " + currentAccount.getHolderName());
        System.out.printf("Available Balance: ₹%.2f%n", currentAccount.getBalance());
        System.out.println("=======================================");
    }

    private void withdrawCash() {
        System.out.println("\n=======================================");
        System.out.println("          CASH WITHDRAWAL");
        System.out.println("=======================================");
        System.out.printf("Available Balance: ₹%.2f%n", currentAccount.getBalance());
        double amount = 0;


        System.out.print("Enter amount: ₹");
        amount = sc.nextDouble();



        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds!");
            System.out.printf("Available Balance: ₹%.2f%n", currentAccount.getBalance());
            return;
        }

        if (currentAccount.withdraw(amount)) {
            TransactionHistory.addTransaction(currentAccount.getAccountNumber(),
                                            "WITHDRAWAL", amount, currentAccount.getBalance());
            System.out.println("Transaction successful!");
            System.out.printf("Amount withdrawn: ₹%.2f%n", amount);
            System.out.printf("Remaining balance: ₹%.2f%n", currentAccount.getBalance());
            System.out.println("Please take your cash and receipt.");
        }
    }

    private void depositCash() {
        System.out.println("\n=======================================");
        System.out.println("          CASH DEPOSIT");
        System.out.println("=======================================");
        System.out.print("Enter deposit amount: ₹");
        double amount = sc.nextFloat();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        currentAccount.deposite((float) amount);
        TransactionHistory.addTransaction(currentAccount.getAccountNumber(),
                                        "DEPOSIT", amount, currentAccount.getBalance());
        System.out.println("Deposit successful!");
        System.out.printf("Amount deposited: ₹%.2f%n", amount);
        System.out.printf("New balance: ₹%.2f%n", currentAccount.getBalance());
    }

    private void transferMoney() {
        System.out.println("\n=======================================");
        System.out.println("          MONEY TRANSFER");
        System.out.println("=======================================");
        System.out.print("Enter recipient's card number: ");
        String recipientCard = sc.next();

        account recipientAccount = account_manager.findAccByCard(recipientCard);
        if (recipientAccount == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (recipientCard.equals(currentAccount.getCardNumber())) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        System.out.print("Enter transfer amount: ₹");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds!");
            return;
        }

        currentAccount.withdraw(amount);
        recipientAccount.deposite((float) amount);

        TransactionHistory.addTransaction(currentAccount.getAccountNumber(),
                                        "TRANSFER OUT", amount, currentAccount.getBalance());
        TransactionHistory.addTransaction(recipientAccount.getAccountNumber(),
                                        "TRANSFER IN", amount, recipientAccount.getBalance());

        System.out.println("Transfer successful!");
        System.out.printf("Amount transferred: ₹%.2f%n", amount);
        System.out.println("To: " + recipientAccount.getHolderName());
        System.out.printf("Remaining balance: ₹%.2f%n", currentAccount.getBalance());
    }

    private void changePIN() {
        System.out.println("\n=======================================");
        System.out.println("            CHANGE PIN");
        System.out.println("=======================================");
        System.out.print("Enter current PIN: ");
        String currentPIN = sc.next();

        if (!currentPIN.equals(currentAccount.getPin())) {
            System.out.println("Incorrect current PIN.");
            return;
        }

        System.out.print("Enter new PIN (4 digits): ");
        String newPIN = sc.next();

        if (newPIN.length() != 4 || !newPIN.matches("\\d{4}")) {
            System.out.println("PIN must be exactly 4 digits.");
            return;
        }

        System.out.print("Confirm new PIN: ");
        String confirmPIN = sc.next();

        if (!newPIN.equals(confirmPIN)) {
            System.out.println("PINs do not match.");
            return;
        }

        currentAccount.setPin(newPIN);
        System.out.println("PIN changed successfully!");
    }

    private void showTransactionHistory() {
        System.out.println("\n=======================================");
        System.out.println("        TRANSACTION HISTORY");
        System.out.println("=======================================");

        List<TransactionHistory.Transaction> transactions =
            TransactionHistory.getTransactions(currentAccount.getAccountNumber());

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Recent Transactions:");
            System.out.println("Date & Time          | Type        | Amount    | Balance");
            System.out.println("--------------------------------------------------------");

            int count = 0;
            for (int i = transactions.size() - 1; i >= 0 && count < 10; i--, count++) {
                System.out.println(transactions.get(i));
            }
        }
        System.out.println("=======================================");
    }
}