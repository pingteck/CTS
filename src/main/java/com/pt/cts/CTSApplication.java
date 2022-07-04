package com.pt.cts;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pt.cts.entity.Account;
import com.pt.cts.entity.BtcUsdt;
import com.pt.cts.entity.EthUsdt;
import com.pt.cts.repository.AccountRepository;
import com.pt.cts.repository.BtcUsdtRepository;
import com.pt.cts.repository.EthUsdtRepository;

@EnableScheduling
@EnableJpaRepositories("com.pt.cts.repository")
@EntityScan("com.pt.cts.entity")
@ComponentScan({ "com.pt.cts.db", "com.pt.cts.exchange", "com.pt.cts.controller" })
@SpringBootApplication
public class CTSApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CTSApplication.class, args);
	}

	@Bean
	CommandLineRunner initDB(final AccountRepository accounts, final BtcUsdtRepository btcusdt,
			final EthUsdtRepository ethusdt) {
		return args -> {
			final Account user = new Account("user");
			user.setBtc(50000);
			accounts.save(user);
			btcusdt.save(new BtcUsdt("binance", 0, 0, new Timestamp(new Date().getTime())));
			btcusdt.save(new BtcUsdt("huobi", 0, 0, new Timestamp(new Date().getTime())));
			ethusdt.save(new EthUsdt("binance", 0, 0, new Timestamp(new Date().getTime())));
			ethusdt.save(new EthUsdt("huobi", 0, 0, new Timestamp(new Date().getTime())));
		};
	}

}
