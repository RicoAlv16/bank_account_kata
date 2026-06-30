package bank.api;

import bank.domain.Account;
import bank.domain.Amount;
import bank.domain.Operation;
import bank.statement.StatementPrinter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final Account account;
    private final StatementPrinter printer;

    // Dependency injection by the constructor
    public AccountServiceImpl(Account account, StatementPrinter printer) {
        this.account = account;
        this.printer = printer;
    }

    @Override
    public void deposit(BigDecimal amount) {
        // Validation of the amount is done automatically here upon instantiation
        account.deposit(new Amount(amount));
    }

    @Override
    public void withdraw(BigDecimal amount) {
        account.withdraw(new Amount(amount));
    }

    @Override
    public void printStatement() {
        List<Operation> operations = new ArrayList<>(account.getOperations());
        // Reverse Chronological Order
        Collections.reverse(operations);
        printer.print(operations);
    }
}