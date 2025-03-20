package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class AddOperationCommand implements Command {
    private final FinanceFacade facade;
    private final OperationType type;
    private final int accountId;
    private final double amount;
    private final LocalDate date;
    private final String description;
    private final int categoryId;

    @Autowired
    public AddOperationCommand(FinanceFacade facade, OperationType type, int accountId, double amount,
                               LocalDate date, String description, int categoryId) {
        this.facade = facade;
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public void execute() {
        Operation op = facade.addOperation(type, accountId, amount, date, description, categoryId);
        System.out.println("Added operation ID: " + op.getId() + " Amount: " + op.getAmount());
    }
}
