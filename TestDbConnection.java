package com.example.productcrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDbConnection {
    public static void main(String[] args) {
        String host = System.getenv("PGHOST");
        String user = System.getenv("PGUSER");
        String db = System.getenv("PGDATABASE");
        String pass = System.getenv("PGPASSWORD");

        if (host == null || user == null || db == null || pass == null) {
            System.err.println("Error: Environment variables PGHOST, PGUSER, PGDATABASE, PGPASSWORD harus di-set");
            System.exit(1);
        }

        String url = String.format("jdbc:postgresql://%s:5432/%s?sslmode=require", host, db);

        try {
            System.out.println("Connecting to: " + url);
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connection successful!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Connection failed:");
            e.printStackTrace();
        }
    }
}
