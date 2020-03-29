package com.springboot.zuul.server;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;


@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
public class SpringbootServicioZuulServerApplication implements CommandLineRunner {

	@Autowired
	private Environment env;
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(SpringbootServicioZuulServerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioZuulServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		for (int i = 0; i < env.getActiveProfiles().length; i++) {
			log.info("--------- INFORMACIÓN DE VARIABLES DE ENTORNO ---------");
			log.info("Active Profile: " + env.getActiveProfiles()[i]);
			log.info("eureka.client.service-url.defaultZone: " + env.getProperty("eureka.client.service-url.defaultZone"));
			log.info("--------------------------------------------------------------------------------------------------");
		}			
	}

}
