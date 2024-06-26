package com.hanaro.hanafun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanafunApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanafunApplication.class, args);
	}

}
