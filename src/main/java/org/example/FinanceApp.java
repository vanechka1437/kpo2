package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
@EntityScan("org.example")
@EnableJpaRepositories("org.example")
public class FinanceApp implements CommandLineRunner {
    private final ApplicationContext context;
    private final FinanceFacade facade;

    public FinanceApp(ApplicationContext context, FinanceFacade facade) {
        this.context = context;
        this.facade = facade;
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceApp.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Add Category\n3. Add Operation\n4. Net Income\n5. Import Data (CSV/JSON/YAML)\n6. Export Data (CSV/JSON/YAML)\n0. Exit");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Account name: ");
                        String name = scanner.nextLine();
                        System.out.print("Initial balance: ");
                        double balance = Double.parseDouble(scanner.nextLine());

                        Command cmd = context.getBean(CreateAccountCommand.class);
                        TimedCommandDecorator timed = context.getBean(TimedCommandDecorator.class);
                        timed.setWrapped(cmd);
                        timed.execute(name, balance);
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
                        System.out.print("Account ID (UUID): ");
                        UUID accId = UUID.fromString(scanner.nextLine());
                        System.out.print("Amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        System.out.print("Type (INCOME/EXPENSE): ");
                        OperationType type = OperationType.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Category ID (UUID): ");
                        UUID catId = UUID.fromString(scanner.nextLine());
                        LocalDate date = LocalDate.now();

                        Command cmd = context.getBean(AddOperationCommand.class);
                        TimedCommandDecorator timed = context.getBean(TimedCommandDecorator.class);
                        timed.setWrapped(cmd);
                        timed.execute(type, accId, amount, date, desc, catId);
                    }
                    case 4 -> {
                        System.out.print("From date (yyyy-mm-dd): ");
                        LocalDate from = LocalDate.parse(scanner.nextLine());
                        System.out.print("To date (yyyy-mm-dd): ");
                        LocalDate to = LocalDate.parse(scanner.nextLine());
                        double net = facade.calculateNetIncome(from, to);
                        System.out.println("Net Income: " + net);
                    }
                    case 5 -> {
                        System.out.print("Enter file path: ");
                        String filePath = scanner.nextLine();
                        System.out.print("Enter file type (csv/json/yaml): ");
                        String fileType = scanner.nextLine().toLowerCase();

                        DataImporter importer = switch (fileType) {
                            case "csv" -> context.getBean(CsvDataImporter.class);
                            case "json" -> context.getBean(JsonDataImporter.class);
                            case "yaml" -> context.getBean(YamlDataImporter.class);
                            default -> null;
                        };

                        if (importer == null) {
                            System.out.println("Unsupported file type.");
                        } else {
                            importer.importData(filePath);
                            System.out.println("Import successful!");
                        }
                    }
                    case 6 -> {
                        System.out.print("Enter file path: ");
                        String filePath = scanner.nextLine();
                        System.out.print("Enter file type (csv/json/yaml): ");
                        String fileType = scanner.nextLine().toLowerCase();

                        DataExporter exporter = switch (fileType) {
                            case "csv" -> context.getBean(CsvDataExporter.class);
                            case "json" -> context.getBean(JsonDataExporter.class);
                            case "yaml" -> context.getBean(YamlDataExporter.class);
                            default -> null;
                        };

                        if (exporter == null) {
                            System.out.println("Unsupported file type.");
                        } else {
                            exporter.exportData(filePath);
                            System.out.println("Export successful!");
                        }
                    }
                    default -> System.out.println("Invalid option! Please choose a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }
}