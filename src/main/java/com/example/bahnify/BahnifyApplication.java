package com.example.bahnify;

import com.example.bahnify.bahnify_stats.ConnectionProbability.ConnectionProbability;
import com.example.bahnify.bahnify_stats.DataInfo.DataInfo;
import com.example.bahnify.bahnify_stats.DatabaseConfig;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.Resources.Direction;
import com.example.bahnify.bahnify_stats.Resources.IdentifiableStop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;


@SpringBootApplication
@RestController
public class BahnifyApplication {

	public static void main(String[] args) {

		DatabaseConfig dbConf = new DatabaseConfig(GlobalDatabaseConnection.configPath);
		GlobalDatabaseConnection.globalDatabaseConnection = new DatabaseConnection(dbConf);

		SpringApplication.run(BahnifyApplication.class, args);
	}

}
