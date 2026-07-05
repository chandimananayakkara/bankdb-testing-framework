package com.bankdb.tests;

import com.bankdb.queries.EmployeeQueries;
import com.bankdb.utils.DBUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TableStructureTest extends BaseTest {


    private List<String> expectedTables = Arrays.asList(
            "employees", "departments", "dept_emp",
            "dept_manager", "salaries", "titles"
    );

    @Test(priority = 1, description = "All 6 tables should exist in database")
    public void testAllTablesExist() {
        System.out.println("\n🔍 TEST: All tables exist...");


        List<Map<String, Object>> results = DBUtils.runQuery(
                EmployeeQueries.GET_ALL_TABLES
        );


        List<String> actualTables = results.stream()
                .map(row -> row.get("TABLE_NAME").toString())
                .collect(Collectors.toList());


        for (String table : expectedTables) {
            boolean exists = actualTables.contains(table);
            Assert.assertTrue(exists,
                    "❌ Table '" + table + "' database එකේ නැහැ!");
            System.out.println("  ✅ Table found: " + table);
        }
    }


    @Test(priority = 2, description = "Employees table should have correct columns")
    public void testEmployeesTableColumns() {
        System.out.println("\n🔍 TEST: Employees table columns...");


        List<String> expectedColumns = Arrays.asList(
                "emp_no", "birth_date", "first_name",
                "last_name", "gender", "hire_date"
        );


        List<String> actualColumns = DBUtils.getColumnNames("employees");


        for (String column : expectedColumns) {
            Assert.assertTrue(actualColumns.contains(column),
                    "❌ Column '" + column + "' employees table එකේ නැහැ!");
            System.out.println("  ✅ Column found: " + column);
        }


        Assert.assertEquals(actualColumns.size(), expectedColumns.size(),
                "❌ Column count mismatch!");
        System.out.println("  ✅ Column count correct: " + actualColumns.size());
    }


    @Test(priority = 3, description = "All tables should have primary keys")
    public void testPrimaryKeysExist() {
        System.out.println("\n🔍 TEST: Primary keys exist...");

        for (String table : expectedTables) {
            List<String> primaryKeys = DBUtils.getPrimaryKeys(table);

            Assert.assertFalse(primaryKeys.isEmpty(),
                    "❌ Table '" + table + "' එකේ Primary Key නැහැ!");
            System.out.println("  ✅ " + table + " → PK: " + primaryKeys);
        }
    }


    @Test(priority = 4, description = "Salaries table structure validation")
    public void testSalariesTableColumns() {
        System.out.println("\n🔍 TEST: Salaries table columns...");

        List<String> expectedColumns = Arrays.asList(
                "emp_no", "salary", "from_date", "to_date"
        );

        List<String> actualColumns = DBUtils.getColumnNames("salaries");

        for (String column : expectedColumns) {
            Assert.assertTrue(actualColumns.contains(column),
                    "❌ Column '" + column + "' salaries table එකේ නැහැ!");
            System.out.println("  ✅ Column found: " + column);
        }
    }
}