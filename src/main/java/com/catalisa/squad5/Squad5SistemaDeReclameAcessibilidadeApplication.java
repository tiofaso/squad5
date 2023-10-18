package com.catalisa.squad5;

import com.catalisa.squad5.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Squad5SistemaDeReclameAcessibilidadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Squad5SistemaDeReclameAcessibilidadeApplication.class, args);
        String jwsSecret = System.getenv("JWS_SECRET");
        System.out.println(jwsSecret);
    }



}