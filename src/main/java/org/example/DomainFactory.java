package org.example;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

public class DomainFactory {
    @Getter
    private static int categoryCounter = 1;

    public static BankAccount createBankAccount(String name, double initialBalance) {
        return new BankAccount(name, initialBalance);
    }

    public static Category createCategory(OperationType type, String name) {
        return new Category(type, name);
    }

    public static Operation createOperation(OperationType type, UUID bankAccountId, double amount, LocalDate date, String description, UUID categoryId) {
        return new Operation(type, bankAccountId, amount, date, description, categoryId);
    }

    public static void setCategoryCounter(int categoryCounter) {
        DomainFactory.categoryCounter = categoryCounter;
    }
}
