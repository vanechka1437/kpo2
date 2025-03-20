package org.example;
import org.springframework.stereotype.Component;

@Component
public class ExportVisitorImpl implements ExportVisitor {
    @Override
    public void visit(BankAccount account) {
        System.out.println("Exporting Bank Account: " + account.getName() + ", Balance: " + account.getBalance());
    }

    @Override
    public void visit(Category category) {
        System.out.println("Exporting Category: " + category.getName());
    }

    @Override
    public void visit(Operation operation) {
        System.out.println("Exporting Operation: " + operation.getDescription() + ", Amount: " + operation.getAmount());
    }
}
