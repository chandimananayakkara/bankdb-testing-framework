package com.bankdb.tests;

import com.bankdb.queries.EmployeeQueries;
import com.bankdb.utils.DBUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class DataValidationTest extends BaseTest {


    @Test(priority = 1, description = "Employee count should be 300024")
    public void testEmployeeCount() {
        System.out.println("\n🔍 TEST: Employee row count...");

        long actualCount = DBUtils.getCount(EmployeeQueries.COUNT_EMPLOYEES);

        Assert.assertEquals(actualCount, 300024L,
                "❌ Employee count wrong!");
        System.out.println("  ✅ Employee count: " + actualCount + " ✓");
    }

    @Test(priority = 2, description = "Department count should be 9")
    public void testDepartmentCount() {
        System.out.println("\n🔍 TEST: Department count...");

        long actualCount = DBUtils.getCount(EmployeeQueries.COUNT_DEPARTMENTS);

        Assert.assertEquals(actualCount, 9L,
                "❌ Department count wrong!");
        System.out.println("  ✅ Department count: " + actualCount + " ✓");
    }


    @DataProvider(name = "criticalColumns")
    public Object[][] criticalColumns() {

        return new Object[][] {
                {"employees",   "emp_no"},
                {"employees",   "first_name"},
                {"employees",   "last_name"},
                {"employees",   "hire_date"},
                {"departments", "dept_name"},
                {"salaries",    "salary"}
        };
    }

    @Test(priority = 3, dataProvider = "criticalColumns",
            description = "Critical columns should have no NULL values")
    public void testNoNullValues(String tableName, String columnName) {
        System.out.println("\n🔍 TEST: NULL check → " + tableName + "." + columnName);


        String query = String.format(EmployeeQueries.COUNT_NULLS, tableName, columnName);
        long nullCount = DBUtils.getCount(query);

        Assert.assertEquals(nullCount, 0L,
                "❌ " + tableName + "." + columnName + " එකේ NULL " + nullCount + "ක් තියෙනවා!");
        System.out.println("  ✅ No NULLs in " + tableName + "." + columnName);
    }


    @Test(priority = 4, description = "No duplicate employee records")
    public void testNoDuplicates() {
        System.out.println("\n🔍 TEST: Duplicate employees...");

        List<Map<String, Object>> duplicates = DBUtils.runQuery(
                EmployeeQueries.FIND_DUPLICATES
        );

        Assert.assertTrue(duplicates.isEmpty(),
                "❌ Duplicate records " + duplicates.size() + "ක් හම්බ උනා!");
        System.out.println("  ✅ No duplicates found!");
    }


    @Test(priority = 5, description = "No negative or extremely high salaries")
    public void testValidSalaryRange() {
        System.out.println("\n🔍 TEST: Salary range validation...");

        List<Map<String, Object>> invalidSalaries = DBUtils.runQuery(
                EmployeeQueries.INVALID_SALARIES
        );

        Assert.assertTrue(invalidSalaries.isEmpty(),
                "❌ Invalid salary records " + invalidSalaries.size() + "ක් තියෙනවා!");
        System.out.println("  ✅ All salaries in valid range (0 - 500,000)");
    }


    @Test(priority = 6, description = "No orphan records in dept_emp table")
    public void testNoOrphanRecords() {
        System.out.println("\n🔍 TEST: Orphan records...");

        List<Map<String, Object>> orphans = DBUtils.runQuery(
                EmployeeQueries.ORPHAN_RECORDS
        );

        Assert.assertTrue(orphans.isEmpty(),
                "❌ Orphan records " + orphans.size() + "ක් තියෙනවා!");
        System.out.println("  ✅ No orphan records!");
    }


    @Test(priority = 7, description = "Hire date must be after birth date")
    public void testHireDateAfterBirthDate() {
        System.out.println("\n🔍 TEST: Hire date > Birth date...");

        List<Map<String, Object>> invalidDates = DBUtils.runQuery(
                EmployeeQueries.INVALID_DATES
        );

        Assert.assertTrue(invalidDates.isEmpty(),
                "❌ " + invalidDates.size() + " employees with hire_date <= birth_date!");
        System.out.println("  ✅ All hire dates are after birth dates!");
    }


    @Test(priority = 8, description = "Gender should only have M or F values")
    public void testGenderValues() {
        System.out.println("\n🔍 TEST: Gender field values...");

        String query = "SELECT DISTINCT gender FROM employees";
        List<Map<String, Object>> genders = DBUtils.runQuery(query);

        Assert.assertEquals(genders.size(), 2,
                "❌ Expected 2 gender values, found: " + genders.size());
        System.out.println("  ✅ Gender values: M, F only ✓");
    }


    @Test(priority = 9, description = "Every employee must have at least one salary")
    public void testEveryEmployeeHasSalary() {
        System.out.println("\n🔍 TEST: All employees have salary records...");

        String query = "SELECT e.emp_no FROM employees e " +
                "LEFT JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE s.emp_no IS NULL";

        List<Map<String, Object>> noSalary = DBUtils.runQuery(query);

        Assert.assertTrue(noSalary.isEmpty(),
                "❌ " + noSalary.size() + " employees without salary records!");
        System.out.println("  ✅ All employees have salary records!");
    }
}