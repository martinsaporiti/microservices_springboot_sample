package com.springboot.items.service;

import java.util.List;

import com.springboot.commons.model.Producto;
import com.springboot.items.model.Item;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	public Producto save(Producto producto);
	public Producto update(Producto producto, Long id);
	public void delete(Long id);
	
}
