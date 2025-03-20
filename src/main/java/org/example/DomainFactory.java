package org.example;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DomainFactory {
    private static int bankAccountCounter = 1;
    private static int categoryCounter = 1;
    private static int operationCounter = 1;

    public static BankAccount createBankAccount(String name, double initialBalance) {
        return new BankAccount(bankAccountCounter++, name, initialBalance);
    }

    public static Category createCategory(OperationType type, String name) {
        return new Category(categoryCounter++, type, name);
    }

    public static Operation createOperation(OperationType type, int bankAccountId, double amount, LocalDate date, String description, int categoryId) {
        return new Operation(operationCounter++, type, bankAccountId, amount, date, description, categoryId);
    }
}
