package com.example.bahnify;

import com.example.bahnify.bahnify_stats.DatabaseConnection;

public class GlobalDatabaseConnection {
    public static DatabaseConnection globalDatabaseConnection;

    public static String configPath = "/root/bahnifyConf/bahnify.config";
    // sftp://root@212.132.100.244/root/bahnifyConf/bahnify.config E:\conf\bahnify.config
}
