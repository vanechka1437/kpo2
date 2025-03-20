package org.example;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountService {
    private final BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Cacheable("accounts")
    public BankAccount getAccountById(UUID id) {
        Optional<BankAccount> account = repository.findById(id);
        return account.orElseThrow(() -> new RuntimeException("Account not found!"));
    }
}
