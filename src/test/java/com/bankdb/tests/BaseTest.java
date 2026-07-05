package com.bankdb.tests;

import com.bankdb.connection.DBConnection;
import com.bankdb.utils.DBUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setup() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("🚀 BankDB Test Framework - STARTING!");
        System.out.println("═══════════════════════════════════════");


        DBConnection.getConnection();
    }

    @AfterSuite
    public void tearDown() {

        DBConnection.closeConnection();

        System.out.println("═══════════════════════════════════════");
        System.out.println("🏁 BankDB Test Framework - FINISHED!");
        System.out.println("═══════════════════════════════════════");
    }
}