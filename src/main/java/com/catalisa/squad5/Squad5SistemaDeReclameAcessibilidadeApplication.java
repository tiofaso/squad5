package com.catalisa.squad5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Squad5SistemaDeReclameAcessibilidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Squad5SistemaDeReclameAcessibilidadeApplication.class, args);
		//System.out.printf("Nova senha" + new BCryptPasswordEncoder().encode("12345"));
	}

}
