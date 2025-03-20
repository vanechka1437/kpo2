package org.example;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class FinanceFacade {
    private final Map<Integer, BankAccount> accounts = new HashMap<>();
    private final Map<Integer, Category> categories = new HashMap<>();
    private final List<Operation> operations = new ArrayList<>();

    public BankAccount addAccount(String name, double balance) {
        BankAccount acc = DomainFactory.createBankAccount(name, balance);
        accounts.put(acc.getId(), acc);
        return acc;
    }

    public Category addCategory(OperationType type, String name) {
        Category cat = DomainFactory.createCategory(type, name);
        categories.put(cat.getId(), cat);
        return cat;
    }

    public Operation addOperation(OperationType type, int accountId, double amount, LocalDate date, String desc, int categoryId) {
        BankAccount acc = accounts.get(accountId);
        if (acc == null) throw new IllegalArgumentException("Account not found");

        if (type == OperationType.EXPENSE) amount = -amount;
        acc.updateBalance(amount);

        Operation op = DomainFactory.createOperation(type, accountId, Math.abs(amount), date, desc, categoryId);
        operations.add(op);
        return op;
    }

    public double calculateNetIncome(LocalDate from, LocalDate to) {
        return operations.stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .mapToDouble(op -> op.getType() == OperationType.INCOME ? op.getAmount() : -op.getAmount())
                .sum();
    }
}