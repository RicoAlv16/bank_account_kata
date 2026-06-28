# Bank Account Kata

A Java implementation of the **Bank Account Kata**, created as part of a technical assessment. The goal: to demonstrate a TDD-oriented approach, clean code, and a suitable architecture—without over-engineering.

## Exercise Context

> *Think of your personal bank account experience. When in doubt, go for the simplest solution.*

The kata requires providing **a service API and its underlying implementation**, without a user interface or persistence. It addresses three functional requirements outlined in three user stories:

| # | User Story | Requirement |
|---|---|---|
| US1 | As a customer, I want to make a **deposit** into my account | To save money |
| US2 | As a customer, I want to make a **withdrawal** from my account | To access some or all of my savings |
| US3 | As a customer, I want to view my transaction **history** (transaction type, date, amount, balance) | To check my transactions |


## Specifications
In accordance with the requirements, this project focuses exclusively on the **Core Domain**:
- **No frameworks** (Pure Java 17+)
- **No persistence** (In-memory management)
- **No GUI / Web interface**

---

## Architectural

### 1. Domain-Driven Model & Clean Code
To demonstrate engineering rigor without over-engineering, the project is organized into three clear packages that respect the Single Responsibility Principle (SRP):
- `bank.domain`: Contains core business rules (`Account`, immutable `Operation` objects using Java `Records`, and business exceptions).
- `bank.api`: Defines entry points and orchestration (`AccountService`, `Clock`).
- `bank.statement`: Manages output and display logic (`StatementPrinter`).

### 2. Financial Precision
All amounts and balances are handled using the **`BigDecimal`** type (verified via `isEqualByComparingTo` in tests). Floating-point types (`double`, `float`) were avoided to prevent rounding errors, which are unacceptable in the banking domain.

### 3. Concurrency & Data Consistency (*Safety by Design*)
Since the account lifecycle runs entirely in-memory without database locks, thread safety is guaranteed directly at the JVM level. The state-mutating methods of the `Account` class are **`synchronized`**, protecting the balance against race conditions during simultaneous multi-threaded access.

### 4. Time Control (Testability)
To reliably validate the reverse-chronological sorting of the bank statement, a `Clock` interface is injected; this allows time to be frozen and controlled during unit and integration tests, thereby avoiding tight coupling with `LocalDateTime.now()`.

---

## Testing Strategy (TDD)

The project was developed using a strict **TDD (Test-Driven Development)** approach (*Red -> Green -> Refactor*).

- **Domain Tests (`AccountTest`)** : Exhaustive validation of nominal and edge cases (rejection of negative or zero amounts, `null` values, or insufficient funds).
- **Integration Tests (`AccountServiceIntegrationTest`)** : Use of a *Stub* for the clock and a *Spy* to capture the data flow, validating orchestration behavior and reverse-chronological sorting without dependency on the machine's locale.
- **The Live Demo (`ConsoleStatementPrinterTest`)**: A demonstration method has been included to visually validate the bank statement rendering in the console.

---

## Installation and Execution

### Prerequisites
- **Java 21**
- **Maven 3.6+**

### Running Tests
To execute the entire unit and integration test suite:
```bash
mvn test
```
<br><br><br><br>

<div align="right" style="color: gray;">Produced by D.A</div>   
 