package com.springboot.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.items.model.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	
	@GetMapping("/products/list")
	public List<Producto> listar();
	
	@GetMapping("/products/view/{id}")
	public Producto detail(@PathVariable Long id);

}
