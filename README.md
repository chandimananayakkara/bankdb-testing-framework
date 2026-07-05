# 🏦 BankDB Test Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange)
![TestNG](https://img.shields.io/badge/TestNG-7.9-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Tests](https://img.shields.io/badge/Tests-20%20Passed-brightgreen)

A **professional-grade Database Test Automation Framework** built
for Banking domain using Java and TestNG.

---

## 🎯 What This Framework Tests

| Test Category | Tests | What It Validates |
|--------------|-------|-------------------|
| 🏗️ Schema Validation | 4 | Tables, Columns, Primary Keys |
| 🔍 Data Integrity | 9 | NULLs, Duplicates, Orphans, Ranges |
| ⚡ Performance | 4 | Complex JOINs, Aggregates, Subqueries |

---

## 🛠️ Tech Stack

| Tool | Purpose |
|------|---------|
| Java 17 | Programming Language |
| TestNG 7.9 | Test Framework |
| JDBC | Database Connectivity |
| MySQL 8.0 | Database (Employees Sample DB) |
| Maven | Build & Dependency Management |

---

## 📊 Database Under Test

**MySQL Employees Sample Database**
- 300,024 Employee Records
- 2,844,047 Salary Records
- 6 Related Tables
- Total: ~4 Million Records

---

## 🔍 Key Test Scenarios

### Schema Validation
- ✅ All 6 tables exist in database
- ✅ Correct columns in each table
- ✅ Primary keys exist for all tables

### Data Integrity
- ✅ No NULL values in critical columns
- ✅ No duplicate employee records
- ✅ No orphan records (referential integrity)
- ✅ Salary values within valid range (0 - 500,000)
- ✅ Hire date always after birth date (business rule)
- ✅ Gender field contains only M or F values
- ✅ Every employee has at least one salary record

### Performance Testing
- ✅ Complex 4-table JOIN executes under 5 seconds
- ✅ Salary aggregation by department under 5 seconds
- ✅ Subquery for above-average earners under 5 seconds
- ✅ Count of 2.8M salary records under 5 seconds

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0 (XAMPP)
- [Employees Sample Database](https://github.com/datacharmer/test_db)

### Setup
```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/bankdb-test-framework.git
cd bankdb-test-framework

# 2. Setup database
cd test_db
mysql -u root < employees.sql

# 3. Update config
# src/main/resources/config.properties
# db.url=jdbc:mysql://localhost:3306/employees
# db.username=root
# db.password=

# 4. Run tests
mvn clean test
```

### Expected Output

```
BankDB Test Automation Suite
Total tests run: 20, Passes: 20, Failures: 0
```


## 📁 Project Structure

```
bankdb-test-framework/
├── src/
│   ├── main/java/com/bankdb/
│   │   ├── connection/
│   │   │   └── DBConnection.java      # Database connection manager
│   │   ├── queries/
│   │   │   └── EmployeeQueries.java   # SQL queries constants
│   │   └── utils/
│   │       └── DBUtils.java           # Query execution utilities
│   └── test/java/com/bankdb/tests/
│       ├── BaseTest.java              # Setup & teardown
│       ├── TableStructureTest.java    # Schema validation
│       ├── DataValidationTest.java    # Data integrity
│       └── QueryPerformanceTest.java  # Performance tests
├── pom.xml
└── testng.xml
```

### Design Patterns Used
* Singleton Pattern - Single DB connection instance
* Page Object equivalent - Queries separated from tests
* Data-Driven Testing - TestNG DataProvider
* Base Test Pattern - Shared setup/teardown

### 👨‍💻 Author
* Chandima Nanayakkara
* QA Automation Engineer
* [LinkedIn](https://www.linkedin.com/in/chandima-nanayakkara/) | [GitHub](https://github.com/chandimananayakkara)