package com.springboot.items.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.commons.model.Producto;
import com.springboot.items.model.Item;
import com.springboot.items.service.ItemService;

@RefreshScope
@RestController
public class ItemController {
	
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;
	
	@Autowired
//	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@GetMapping("/list")
	public List<Item> list(){
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/view/{id}/cantidad/{cantidad}")
	public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
	
	
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		item.setCantidad(cantidad);
		return item;
		
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String puerto){
		log.info(texto);
		
		
		Map<String,String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		
		
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
			json.put("eureka.url", env.getProperty("eureka.client.service-url.defaultZone"));
		} else if(env.getActiveProfiles().length > 0 ) {
			json.put("eureka.url", env.getProperty("eureka.client.service-url.defaultZone"));
		}
		
		return new ResponseEntity<Map<String,String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto create(@RequestBody Producto producto) {
		return itemService.save(producto);
	}
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto create(@RequestBody Producto producto, @PathVariable Long id) {
		return itemService.update(producto, id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}
}
