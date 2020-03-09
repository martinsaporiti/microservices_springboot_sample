package com.springboot.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.productos.model.entity.Producto;
import com.springboot.productos.service.ProductoService;

@RestController()
@RequestMapping("/products")
public class ProductoController {
	
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	

	@Autowired
	private ProductoService service;
	
	@GetMapping("/list")
	public List<Producto> list(){
		return service.findAll().stream().map(producto -> {
//			producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/view/{id}")
	public Producto detail(@PathVariable Long id){
		Producto producto = service.findById(id);
//		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
//		boolean ok = true;
//		if (!ok) {throw new Exception("No se pudo cargr el producto");}
		
//		try {
//			Thread.sleep(2000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
		return service.findById(id);
	}
}
