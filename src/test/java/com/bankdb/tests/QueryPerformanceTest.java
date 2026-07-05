package com.bankdb.tests;

import com.bankdb.queries.EmployeeQueries;
import com.bankdb.utils.DBUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class QueryPerformanceTest extends BaseTest {


    private static final long MAX_TIME = 5000;


    @Test(priority = 1, description = "Complex JOIN should run under 5 seconds")
    public void testComplexJoinPerformance() {
        System.out.println("\n🔍 PERFORMANCE TEST: Complex JOIN...");


        long startTime = System.currentTimeMillis();


        List<Map<String, Object>> results = DBUtils.runQuery(
                EmployeeQueries.EMPLOYEE_FULL_DETAILS
        );


        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;


        Assert.assertFalse(results.isEmpty(), "❌ No results returned!");
        Assert.assertTrue(timeTaken < MAX_TIME,
                "❌ Query too slow! Took " + timeTaken + "ms (max: " + MAX_TIME + "ms)");

        System.out.println("  ⏱️ Time: " + timeTaken + "ms");
        System.out.println("  📊 Rows: " + results.size());
        System.out.println("  ✅ Performance OK!");
    }


    @Test(priority = 2, description = "Salary aggregation should run under 5 seconds")
    public void testAggregatePerformance() {
        System.out.println("\n🔍 PERFORMANCE TEST: Salary aggregation...");

        long startTime = System.currentTimeMillis();

        List<Map<String, Object>> results = DBUtils.runQuery(
                EmployeeQueries.AVG_SALARY_BY_DEPT
        );

        long timeTaken = System.currentTimeMillis() - startTime;


        for (Map<String, Object> row : results) {
            System.out.println("  📊 " + row.get("dept_name")
                    + " → Avg Salary: $" + row.get("avg_salary"));
        }

        Assert.assertTrue(timeTaken > MAX_TIME,
                "❌ Aggregate query too slow! " + timeTaken + "ms");
        System.out.println("  ⏱️ Time: " + timeTaken + "ms ✅");
    }


    @Test(priority = 3, description = "Subquery should run under 5 seconds")
    public void testSubqueryPerformance() {
        System.out.println("\n🔍 PERFORMANCE TEST: Subquery...");

        String query = "SELECT e.first_name, e.last_name, s.salary " +
                "FROM employees e " +
                "JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE s.to_date = '9999-01-01' " +
                "AND s.salary > (" +
                "  SELECT AVG(salary) FROM salaries " +
                "  WHERE to_date = '9999-01-01'" +
                ") LIMIT 50";

        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> results = DBUtils.runQuery(query);
        long timeTaken = System.currentTimeMillis() - startTime;

        Assert.assertFalse(results.isEmpty(), "❌ No above-average earners!");
        Assert.assertTrue(timeTaken < MAX_TIME,
                "❌ Subquery too slow! " + timeTaken + "ms");

        System.out.println("  📊 Above-average earners: " + results.size());
        System.out.println("  ⏱️ Time: " + timeTaken + "ms ✅");
    }


    @Test(priority = 4, description = "Counting 2.8M salary records should be fast")
    public void testLargeTableCountPerformance() {
        System.out.println("\n🔍 PERFORMANCE TEST: Count 2.8M records...");

        long startTime = System.currentTimeMillis();
        long count = DBUtils.getCount(EmployeeQueries.COUNT_SALARIES);
        long timeTaken = System.currentTimeMillis() - startTime;

        Assert.assertTrue(count > 2000000, "❌ Salary count too low!");
        Assert.assertTrue(timeTaken < MAX_TIME,
                "❌ Count too slow! " + timeTaken + "ms");

        System.out.println("  📊 Total salary records: " + count);
        System.out.println("  ⏱️ Time: " + timeTaken + "ms ✅");
    }
}