package com.msk610.metasandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
public class MetasandraApplication {

	public static void main(String[] args) {

	    SpringApplication.run(MetasandraApplication.class, args);
	}

}

