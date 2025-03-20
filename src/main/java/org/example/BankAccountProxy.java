package org.example;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class BankAccountProxy {
    private final BankAccountService bankAccountService;
    private final Map<UUID, BankAccount> cache = new HashMap<>();

    public BankAccountProxy(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public BankAccount getAccount(UUID id) {
        return cache.computeIfAbsent(id, bankAccountService::getAccountById);
    }
}