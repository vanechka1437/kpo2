package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FinanceFacade {
    private final OperationRepository operationRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FinanceFacade(OperationRepository operationRepository,
                         BankAccountRepository bankAccountRepository,
                         CategoryRepository categoryRepository) {
        this.operationRepository = operationRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public BankAccount addAccount(String name, double balance) {
        BankAccount acc = new BankAccount(name, balance);
        acc = bankAccountRepository.save(acc);
        System.out.println("DEBUG: Created account - " + acc.getId());
        return acc;
    }

    @Transactional
    public Operation addOperation(OperationType type, UUID accountId, double amount, LocalDate date, String description, UUID categoryId) {
        BankAccount acc = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        double adjustedAmount = (type == OperationType.EXPENSE) ? -Math.abs(amount) : Math.abs(amount);
        acc.updateBalance(adjustedAmount);
        bankAccountRepository.save(acc);

        Operation op = new Operation(type, accountId, adjustedAmount, date, description, categoryId);
        operationRepository.save(op);
        System.out.println("DEBUG: Operation added - " + op.getId());
        return op;
    }

    @Transactional
    public Category addCategory(OperationType type, String name) {
        Category cat = new Category(type, name);
        cat = categoryRepository.save(cat);
        System.out.println("DEBUG: Category saved with ID=" + cat.getId());
        return cat;
    }

    public double calculateNetIncome(LocalDate from, LocalDate to) {
        List<Operation> operations = operationRepository.findAll();

        return operations.stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .mapToDouble(op -> op.getType() == OperationType.INCOME ? op.getAmount() : -op.getAmount())
                .sum();
    }

    public List<Operation> getOperations() {
        return operationRepository.findAll();
    }
}
