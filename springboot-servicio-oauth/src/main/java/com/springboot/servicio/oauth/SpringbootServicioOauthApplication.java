package com.springboot.servicio.oauth;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioOauthApplication implements CommandLineRunner{


	
	@Autowired
	private Environment env;
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(SpringbootServicioOauthApplication.class);

	
	
	@Autowired
	BCryptPasswordEncoder passwordEncode;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "12345";
		
		for (int i = 0; i < 4; i++) {
			String passwordBCrypt = passwordEncode.encode(password);
			System.out.println(passwordBCrypt);
		}
		

		for (int i = 0; i < env.getActiveProfiles().length; i++) {
			log.info("--------- INFORMACIÓN DE VARIABLES DE ENTORNO ---------");
			log.info("Active Profile: " + env.getActiveProfiles()[i]);
			log.info("eureka.client.service-url.defaultZone: " + env.getProperty("eureka.client.service-url.defaultZone"));
			log.info("spring.zipkin.base-url: " + env.getProperty("spring.zipkin.base-url"));
			log.info("spring.activemq.broker-url: " + env.getProperty("spring.activemq.broker-url"));
			log.info("--------------------------------------------------------------------------------------------------");
		}
		
	}

	
}
