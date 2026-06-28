# Technical choice - Bank Account Kata


## Conception choice: Pure Java

### Why no framework? (Spring Boot, etc.) ?

The requirement is explicit: "go for the simplest solution," and it rules out any persistence or UI. Introducing Spring Boot here would amount to **over-engineering**—offering no benefits (no web layer, no complex dependency injection, no database) while incurring a disproportionate cost in terms of complexity and configuration. Opting for **plain Java + Maven + JUnit 5** demonstrates a mastery of fundamentals that is independent of frameworks.

### Simple layered architecture

```
src/main/java/bank/
├── domain/
│   ├── Account.java                        → core business (deposit, withdraw, balance)
│   ├── Operation.java                      → value object immuable (type, date, amount, post-transaction balance)
│   ├── OperationType.java                  → enum (DEPOSIT, WITHDRAWAL)
│   └── InsufficientBalanceException.java   → explicit business exception
├── application/
│   └── AccountService.java                 → Exposed service API (facade)
└── statement/
│    ├── StatementPrinter.java              → interface (port) for displaying the reading
│    └── ConsoleStatementPrinter.java       → consol implémentation
src/test/java/bank/
├── api/
│   ├── AccountServiceIntegrationTest.java  → unit test for account service about fonctionnal orchestration
├── domain/
│   ├── AccountTest.java                    → units tests for account business system (deposit, withdraw, solde)
├── statement/
│   └── ConsoleStatementPrinterTest.java    → unit test for statement display
```

| Layer         | Responsability                           | Justification |
|---------------|------------------------------------------|---|
| `domain`      | Pure business rules, without any I/O     | 100% unit-testable, no external dependencies |
| `application` | Service entry point, orchestration | Corresponds to the "service API" expected by the problem statement. |
| `statement`   | Formatting and displaying the statement         | Display/business logic separation (SRP); injectable interface for easy testing |

### Applied Engineering Principles

- **TDD**: Every behavior is driven by a test written before the code (Red → Green → Refactor cycle).
- **BigDecimal instead of `double`**: Avoids rounding errors on amounts—non-negotiable in a financial context.
- **Immutability**: Once recorded, an `Operation` is never modified, ensuring history reliability (traceability/audit).
- **Typed domain exceptions**: e.g., `InsufficientBalanceException` rather than returning `false` or `null`, ensuring errors are explicit and manageable by the caller.
- **Dependency Inversion without a framework**: `StatementPrinter` is an interface injected via the constructor—allowing `AccountService` to be tested without depending on `System.out`.
- **No Repository/DAO**: Persistence is explicitly out of scope; history is kept in memory (`List<Operation>`) within the `Account`.

### Deliberately Excluded

- No UI (interactive CLI, web, etc.)
- No persistence (database, files)
- No dependency injection framework
- No design patterns not justified by a real need within the kata (Builder, Factory, Repository, etc.)

## Testing Strategy

- **JUnit 5** for unit tests.
- **AssertJ** for fluent, readable assertions.
- Test naming using the *Given/When/Then* style, making them clear even to non-technical readers (e.g., `should_throw_when_withdrawal_exceeds_balance`).
- Coverage of both nominal cases **and** edge cases: negative/zero amounts, insufficient balance, empty history, multiple operations.



## Stack technique

| Outil | Usage |
|---|---|
| Java 17 | Langage |
| Maven | Build & gestion des dépendances |
| JUnit 5 | Framework de test |
| AssertJ | Assertions fluides |

## Security

Dependencies are kept up to date to avoid known vulnerabilities (e.g., CVE-2026-24400 in AssertJ, fixed by upgrading to version ≥ 3.27.7).

## Potential enhancements (intentionally out of scope for this kata)

- Concurrency management (`Account` is not thread-safe in its current state—would need synchronization for actual concurrent access)
- Persistence (Repository pattern) if integration with a real database were required
- Internationalization of date and amount formats in the statement
- REST API if HTTP exposure became necessary