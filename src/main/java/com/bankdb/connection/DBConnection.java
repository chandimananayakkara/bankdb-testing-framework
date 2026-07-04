package com.bankdb.connection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBConnection {

    private static Connection connection;


    public static Connection getConnection() {

        if (connection == null) {
            try {

                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(
                        "src/main/resources/config.properties"
                );
                props.load(fis);


                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");


                connection = DriverManager.getConnection(url, username, password);

                System.out.println("✅ Database Connected");

            } catch (Exception e) {
                System.out.println("❌ Database connection failed.");
                e.printStackTrace();
            }
        }

        return connection;
    }


    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("✅ Database connection closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}