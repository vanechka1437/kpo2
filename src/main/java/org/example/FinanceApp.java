package org.example;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.util.Scanner;

@SpringBootApplication
public class FinanceApp implements CommandLineRunner {
    private final FinanceFacade facade;

    public FinanceApp(FinanceFacade facade) {
        this.facade = facade;
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceApp.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Add Category\n3. Add Operation\n4. Net Income\n0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) break;

            Command cmd = null;
            switch (choice) {
                case 1 -> {
                    System.out.print("Account name: ");
                    String name = scanner.nextLine();
                    System.out.print("Initial balance: ");
                    double balance = Double.parseDouble(scanner.nextLine());
                    cmd = new CreateAccountCommand(facade, name, balance);
                }
                case 2 -> {
                    System.out.print("Category name: ");
                    String name = scanner.nextLine();
                    System.out.print("Type (INCOME/EXPENSE): ");
                    OperationType type = OperationType.valueOf(scanner.nextLine().toUpperCase());
                    Category cat = facade.addCategory(type, name);
                    System.out.println("Created category: " + cat.getName() + " (ID: " + cat.getId() + ")");
                }
                case 3 -> {
                    System.out.print("Account ID: ");
                    int accId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Type (INCOME/EXPENSE): ");
                    OperationType type = OperationType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Category ID: ");
                    int catId = Integer.parseInt(scanner.nextLine());
                    LocalDate date = LocalDate.now();

                    cmd = new AddOperationCommand(facade, type, accId, amount, date, desc, catId);
                }
                case 4 -> {
                    System.out.print("From date (yyyy-mm-dd): ");
                    LocalDate from = LocalDate.parse(scanner.nextLine());
                    System.out.print("To date (yyyy-mm-dd): ");
                    LocalDate to = LocalDate.parse(scanner.nextLine());
                    double net = facade.calculateNetIncome(from, to);
                    System.out.println("Net Income: " + net);
                }
            }

            if (cmd != null) {
                Command timed = new TimedCommandDecorator(cmd);
                timed.execute();
            }
        }

        System.out.println("Goodbye!");
    }
}
