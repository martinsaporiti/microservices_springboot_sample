package com.springboot.items.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springboot.commons.model.Producto;
import com.springboot.items.model.Item;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://servicio-productos/products/list", Producto[].class));
		return productos.stream().map( p -> new Item(p, 1)).collect(Collectors.toList());
	}

	
	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVar = new HashMap<String, String>();
		pathVar.put("id", id.toString());
		Producto producto = restTemplate.getForObject("http://servicio-productos/products/view/{id}", Producto.class, pathVar);
		return new Item(producto, cantidad);
	}


	@Override
	public Producto save(Producto producto) {
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		ResponseEntity<Producto> response = restTemplate.exchange("http://servicio-productos/products/create", HttpMethod.POST, body, Producto.class);
		return response.getBody();
	}


	@Override
	public Producto update(Producto producto, Long id) {
		Map<String, String> pathVar = new HashMap<String, String>();
		pathVar.put("id", id.toString());
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		ResponseEntity<Producto> response = restTemplate.exchange("http://servicio-productos/products/edit/{id}", 
				HttpMethod.PUT, body, Producto.class, pathVar);		
		return response.getBody();
	}


	@Override
	public void delete(Long id) {
		Map<String, String> pathVar = new HashMap<String, String>();
		pathVar.put("id", id.toString());
		restTemplate.delete("http://servicio-productos/products/delete/{id}", pathVar);	
	}

}
