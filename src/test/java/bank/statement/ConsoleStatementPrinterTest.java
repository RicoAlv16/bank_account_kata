package bank.statement;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class ConsoleStatementPrinterTest {

    @Test
    void run_statement_display() {
        System.out.println("\n=== BANK STATEMENT ===");

        // Component initialization
        bank.domain.Account account = new bank.domain.Account();
        bank.api.Clock realClock = java.time.LocalDateTime::now;
        bank.statement.StatementPrinter consolePrinter = new bank.statement.ConsoleStatementPrinter();

        bank.api.AccountService bankService = new bank.api.AccountServiceImpl(account, realClock, consolePrinter);

        // Customer life scenario
        bankService.deposit(new BigDecimal("1000.00"));
        bankService.deposit(new BigDecimal("2300.50"));
        bankService.withdraw(new BigDecimal("450.00"));

        // Finale printing
        bankService.printStatement();

        System.out.println("=== BANK KATA ===\n");
    }
}