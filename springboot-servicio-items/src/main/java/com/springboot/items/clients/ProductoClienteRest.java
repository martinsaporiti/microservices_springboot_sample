package com.springboot.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.items.model.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	
	@GetMapping("/products/list")
	public List<Producto> listar();
	
	@GetMapping("/products/view/{id}")
	public Producto detail(@PathVariable Long id);
	
	
	@PostMapping("/products/create")
	public Producto create(@RequestBody Producto producto);

	
	@PutMapping("/products/edit/{id}")
	public Producto update(@RequestBody Producto producto, @PathVariable Long id);
	
	@DeleteMapping("/products/delete/{id}")
	public void delete(@PathVariable Long id);
}
