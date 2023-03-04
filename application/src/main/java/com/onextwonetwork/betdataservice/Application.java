package com.onextwonetwork.betdataservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		LOGGER.info("Bet data service Application ready \uD83D\uDC4C \uD83C\uDF7E");
	}

}
