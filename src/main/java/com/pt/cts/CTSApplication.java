package com.pt.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories("com.pt.cts.db.repository.*")
@EntityScan("com.pt.cts.db.entity")
@SpringBootApplication
public class CTSApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CTSApplication.class, args);
	}

}
