package com.bankdb.queries;

public class EmployeeQueries {

    // ===== TABLE VALIDATION QUERIES =====

    public static final String GET_ALL_TABLES =
            "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = 'employees'";

    // ===== ROW COUNT QUERIES =====

    public static final String COUNT_EMPLOYEES =
            "SELECT COUNT(*) FROM employees";

    public static final String COUNT_DEPARTMENTS =
            "SELECT COUNT(*) FROM departments";

    public static final String COUNT_SALARIES =
            "SELECT COUNT(*) FROM salaries";

    // ===== DATA INTEGRITY QUERIES =====

    public static final String COUNT_NULLS =
            "SELECT COUNT(*) FROM %s WHERE %s IS NULL";

    // Duplicate employees check
    public static final String FIND_DUPLICATES =
            "SELECT emp_no, COUNT(*) as cnt FROM employees " +
                    "GROUP BY emp_no HAVING cnt > 1";

    // Invalid salary check (minus salary, too high salary)
    public static final String INVALID_SALARIES =
            "SELECT emp_no, salary FROM salaries " +
                    "WHERE salary < 0 OR salary > 500000";

    public static final String ORPHAN_RECORDS =
            "SELECT de.emp_no FROM dept_emp de " +
                    "LEFT JOIN employees e ON de.emp_no = e.emp_no " +
                    "WHERE e.emp_no IS NULL";

    public static final String INVALID_DATES =
            "SELECT emp_no, birth_date, hire_date FROM employees " +
                    "WHERE hire_date <= birth_date";

    // ===== JOIN QUERIES =====

    // Employee with department and salary info
    public static final String EMPLOYEE_FULL_DETAILS =
            "SELECT e.emp_no, e.first_name, e.last_name, " +
                    "d.dept_name, s.salary, t.title " +
                    "FROM employees e " +
                    "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                    "JOIN departments d ON de.dept_no = d.dept_no " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "JOIN titles t ON e.emp_no = t.emp_no " +
                    "WHERE s.to_date = '9999-01-01' " +
                    "AND de.to_date = '9999-01-01' " +
                    "AND t.to_date = '9999-01-01' " +
                    "LIMIT 1000";

    // Average salary per department
    public static final String AVG_SALARY_BY_DEPT =
            "SELECT d.dept_name, ROUND(AVG(s.salary), 2) as avg_salary " +
                    "FROM salaries s " +
                    "JOIN dept_emp de ON s.emp_no = de.emp_no " +
                    "JOIN departments d ON de.dept_no = d.dept_no " +
                    "WHERE s.to_date = '9999-01-01' " +
                    "AND de.to_date = '9999-01-01' " +
                    "GROUP BY d.dept_name " +
                    "ORDER BY avg_salary DESC";
}