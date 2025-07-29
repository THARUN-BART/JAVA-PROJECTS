import java.util.Scanner;
import USER.user_main;
import ADMIN.admin_main;

public class Launcher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        user_main user = new user_main();
        admin_main admin = new admin_main();

        System.out.println("=======================================");
        System.out.println("    SMART ATM SIMULATOR SYSTEM");
        System.out.println("=======================================");
        System.out.println();

        while (true) {
            System.out.println("Select Role:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                sc.next();
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        admin.runAdminSession();
                        break;
                    case 2:
                        user.runUserSession();
                        break;
                    case 3:
                        System.out.println("Thanks for using our ATM services!");
                        System.out.println("Have a great day!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}