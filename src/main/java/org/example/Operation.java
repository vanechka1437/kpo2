package org.example;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Operation {
    private final int id;
    private final OperationType type;
    private final int bankAccountId;
    private final double amount;
    private final LocalDate date;
    private final String description;
    private final int categoryId;

    public Operation(int id, OperationType type, int bankAccountId, double amount, LocalDate date, String description, int categoryId) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

}

