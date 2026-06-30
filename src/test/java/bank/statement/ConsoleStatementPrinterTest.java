package bank.statement;

import bank.api.AccountService;
import bank.api.AccountServiceImpl;
import bank.domain.Account;
import bank.domain.Clock;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class ConsoleStatementPrinterTest {

    @Test
    void run_statement_display() {
        // Given: a separation line for clean terminal reading
        System.out.println("\n=== BANK STATEMENT ===");

        // Given: real production-ready implementations wired together
        Clock realClock = LocalDateTime::now;
        Account account = new Account(realClock);
        StatementPrinter consolePrinter = new bank.statement.ConsoleStatementPrinter();
        AccountService bankService = new AccountServiceImpl(account, consolePrinter);

        // When: a standard customer flow is executed
        bankService.deposit(new BigDecimal("1000.00"));
        bankService.deposit(new BigDecimal("300.50"));
        bankService.withdraw(new BigDecimal("450.00"));

        // Then: it prints the formatted log tables visually in the console
        bankService.printStatement();

        System.out.println("=== BANK KATA ===\n");
    }
}