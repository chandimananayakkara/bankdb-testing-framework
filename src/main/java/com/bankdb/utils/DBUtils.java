package com.bankdb.utils;

import com.bankdb.connection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

     public static List<Map<String, Object>> runQuery(String sql) {
        List<Map<String, Object>> results = new ArrayList<>();

        try {

            Connection conn = DBConnection.getConnection();

            Statement stmt = conn.createStatement();

           ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

            rs.close();
            stmt.close();

            System.out.println("✅ Query results: " + results.size() + " rows");

        } catch (SQLException e) {
            System.out.println("❌ Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }


    public static long getCount(String sql) {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                long count = rs.getLong(1);
                rs.close();
                stmt.close();
                return count;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("❌ Count query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }


    public static List<String> getColumnNames(String tableName) {
        List<String> columns = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);

            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }


    public static List<String> getPrimaryKeys(String tableName) {
        List<String> keys = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);

            while (rs.next()) {
                keys.add(rs.getString("COLUMN_NAME"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return keys;
    }
}