package ADMIN;

import java.util.Random;
import java.util.Scanner;
import USER.account;
import USER.account_manager;

public class admin_main {
    private Scanner sc;
    Random rd=new Random();

    public admin_main() {
        this.sc = new Scanner(System.in);
    }

    private String generateCardNumber() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 16; i++) {
        sb.append(rd.nextInt(10)); // Appends digits 0–9
    }
    return sb.toString();
    }
    public void runAdminSession() {
        System.out.println("=======================================");
        System.out.println("        ADMIN CONTROL PANEL");
        System.out.println("=======================================");

        if (authenticateAdmin()) {
            showAdminMenu();
        } else {
            System.out.println("Authentication failed. Access denied.");
        }
    }

    private boolean authenticateAdmin() {
        System.out.print("Enter Admin Username: ");
        String username = sc.next();
        System.out.print("Enter Admin Password: ");
        String password = sc.next();
        return "bart-simpson".equals(username) && "Tharun@2006".equals(password);
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("\n=======================================");
            System.out.println("           ADMIN MENU");
            System.out.println("=======================================");
            System.out.println("1. Add New Account");
            System.out.println("2. Remove Account");
            System.out.println("3. View All Accounts");
            System.out.println("4. System Statistics");
            System.out.println("5. Exit Admin Panel");
            System.out.print("Select an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> addNewAccount();
                case 2 -> removeAccount();
                case 3 -> viewAllAccounts();
                case 4 -> showSystemStats();
                case 5 -> {
                    System.out.println("Exiting Admin Panel...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addNewAccount() {
        System.out.println("\n=======================================");
        System.out.println("          ADD NEW ACCOUNT");
        System.out.println("=======================================");

        sc.nextLine();
        String accountNumber;
        while (true) {
            System.out.print("Enter Account Number (10 digits): ");
            accountNumber = sc.nextLine().trim();
            if (accountNumber.matches("\\d{10}")) {
                break;
            }
            System.out.println("Invalid input. Please enter exactly 10 digits.");
        }

        String cardNumber = generateCardNumber();
        System.out.println("Your Card NUmber:" + cardNumber);

        System.out.print("Enter PIN (4 digits): ");
        String pin = sc.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String holderName = sc.nextLine();

        System.out.print("Enter Initial Balance: ₹");
        double balance = sc.nextDouble();

        account newAccount = new account(accountNumber, cardNumber, pin, holderName, balance);
        account_manager.addAccount(newAccount);

        System.out.println("Account created successfully!");
        System.out.println("Account Details:");
        System.out.println(newAccount);
    }

    private void removeAccount() {
        System.out.println("\n=======================================");
        System.out.println("          REMOVE ACCOUNT");
        System.out.println("=======================================");
        System.out.print("Enter Card Number to remove: ");
        String cardNumber = sc.next();

        if (account_manager.removeAcc(cardNumber)) {
            System.out.println("Account deactivated successfully!");
        } else {
            System.out.println("Account not found.");
        }
    }

    private void viewAllAccounts() {
        System.out.println("\n=======================================");
        System.out.println("          ALL ACCOUNTS");
        System.out.println("=======================================");
        for (account acc : account_manager.getAllAccounts()) {
            if (acc.isActive()) {
                System.out.println(acc);
                System.out.println("---------------------------------------");
            }
        }
    }

    private void showSystemStats() {
        System.out.println("\n=======================================");
        System.out.println("        SYSTEM STATISTICS");
        System.out.println("=======================================");
        System.out.println("Total Active Accounts: " + account_manager.getTotalAccount());
        System.out.printf("Total System Balance: ₹%.2f%n", account_manager.getTotalBalance());
        System.out.println("ATM Status: Online");
        System.out.println("Last System Update: " + java.time.LocalDateTime.now());
    }
}