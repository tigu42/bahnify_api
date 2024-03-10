package com.example.bahnify.bahnify_stats;

import java.io.FileInputStream;
import java.util.Properties;

public class DatabaseConfig {

    private final Properties properties;

    public Properties getProperties() {
        return this.properties;
    }

    public DatabaseConfig(String path) {
        properties = new Properties();
        try (FileInputStream stream = new FileInputStream(path))
        {
            properties.load(stream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
