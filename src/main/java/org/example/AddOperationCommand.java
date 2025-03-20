package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class AddOperationCommand implements Command {
    private final FinanceFacade facade;

    @Autowired
    public AddOperationCommand(FinanceFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute(Object... args) {
        if (args.length != 6) {
            System.out.println("Error: Expected 6 arguments, received " + args.length);
            throw new IllegalArgumentException("Expected 6 arguments: OperationType, UUID, double, LocalDate, String, UUID");
        }

        if (!(args[0] instanceof OperationType type) ||
                !(args[1] instanceof UUID accountId) ||
                !(args[2] instanceof Double amount) ||
                !(args[3] instanceof LocalDate date) ||
                !(args[4] instanceof String description) ||
                !(args[5] instanceof UUID categoryId)) {
            throw new IllegalArgumentException("Invalid argument types.");
        }

        Operation op = facade.addOperation(type, accountId, amount, date, description, categoryId);
        System.out.println("Added operation ID: " + op.getId() + " Amount: " + op.getAmount());
    }
}
