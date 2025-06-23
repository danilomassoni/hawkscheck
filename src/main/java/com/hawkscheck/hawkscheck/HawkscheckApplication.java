package com.hawkscheck.hawkscheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HawkscheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(HawkscheckApplication.class, args);
	}

}
