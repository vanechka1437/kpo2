package org.example;
import lombok.Getter;

@Getter
public class BankAccount {
    private final int id;
    private final String name;
    private double balance;

    public BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void updateBalance(double amount) { this.balance += amount; }
}

