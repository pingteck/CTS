package com.pt.cts.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatabaseService {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	public void executeStatement(final String sql) {
		try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
				var statement = connection.createStatement()) {
			statement.executeUpdate(sql);
			connection.commit();
		} catch (final SQLException e) {
			log.error(e.toString());
		}
	}

}
