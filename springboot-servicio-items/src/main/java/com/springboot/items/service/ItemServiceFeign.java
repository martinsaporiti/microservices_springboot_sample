package com.springboot.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.springboot.items.clients.ProductoClienteRest;
import com.springboot.items.model.Item;
import com.springboot.items.model.Producto;


@Service
@Primary
public class ItemServiceFeign implements ItemService {
	
	@Autowired
	private ProductoClienteRest clientFeign;
	
	@Override
	public List<Item> findAll() {
		return clientFeign.listar().stream().map( p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clientFeign.detail(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		return clientFeign.create(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		return clientFeign.update(producto, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);
	}

}
