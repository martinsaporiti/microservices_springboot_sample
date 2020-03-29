package com.springboot.servicio.usuarios;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;

@EntityScan({"com.commons.usuarios.model"})
@SpringBootApplication
public class SpringbootServicioUsuariosApplication implements CommandLineRunner{

	
	
	@Autowired
	private Environment env;
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(SpringbootServicioUsuariosApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosApplication.class, args);
			
	}


	@Override
	public void run(String... args) throws Exception {
		
		for (int i = 0; i < env.getActiveProfiles().length; i++) {
			log.info("--------- INFORMACIÓN DE VARIABLES DE ENTORNO ---------");
			log.info("Active Profile: " + env.getActiveProfiles()[i]);
			log.info("eureka.client.service-url.defaultZone: " + env.getProperty("eureka.client.service-url.defaultZone"));
			log.info("spring.zipkin.base-url: " + env.getProperty("spring.zipkin.base-url"));
			log.info("spring.activemq.broker-url: " + env.getProperty("spring.activemq.broker-url"));
			log.info("spring.datasource.url: " + env.getProperty("spring.datasource.url"));
			log.info("--------------------------------------------------------------------------------------------------");
		}		
	}

}
