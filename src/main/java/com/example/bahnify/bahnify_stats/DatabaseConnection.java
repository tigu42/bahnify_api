package com.example.bahnify.bahnify_stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection(DatabaseConfig config) {
        Properties props = config.getProperties();
        try {
            this.connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"), props.getProperty("db.password"));
            System.out.println("Connection to database has been established");
        }
        catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
