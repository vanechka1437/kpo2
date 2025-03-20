package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommand implements Command {
    private final FinanceFacade facade;
    private final String name;
    private final double balance;

    @Autowired
    public CreateAccountCommand(FinanceFacade facade, String name, double balance) {
        this.facade = facade;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void execute() {
        BankAccount account = facade.addAccount(name, balance);
        System.out.println("Created account: " + account.getName() + " (ID: " + account.getId() + ")");
    }
}
