package com.onextwonetwork.betdataservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "com.onextwonetwork.betdataservice.dao")
@PropertySource("/application.properties")
@EnableTransactionManagement
public class DatasourceConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;

	@Value("${jdbc.url}")
	private String dbUrl;

	@Value("${jdbc.user}")
	private String user;

	@Value("${jdbc.pass}")
	private String password;

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
//		System.out.println(":::::::::::driverClassName:::::::::"+driverClassName);
		dataSource.setUrl(dbUrl);
//		System.out.println(":::::::::::dbUrl:::::::::"+dbUrl);
		dataSource.setUsername(user);
//		System.out.println(":::::::::::user:::::::::"+user);
		dataSource.setPassword(password);
//		System.out.println(":::::::::::password:::::::::"+password);

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
																	   DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.onextwonetwork.betdataservice")
				.build();
	}
}