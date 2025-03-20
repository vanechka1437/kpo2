package org.example;

import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommand implements Command {
    private final FinanceFacade facade;

    public CreateAccountCommand(FinanceFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute(Object... args) {
        if (args.length != 2 || !(args[0] instanceof String name) || !(args[1] instanceof Double)) {
            throw new IllegalArgumentException("Expected 2 arguments: String name, double balance. Received: " + args.length);
        }

        double balance = (Double) args[1];

        BankAccount account = facade.addAccount(name, balance);
        System.out.println("Created account: " + account.getName() + " (ID: " + account.getId() + ")");
    }
}
