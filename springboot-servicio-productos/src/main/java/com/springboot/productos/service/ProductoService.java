package com.springboot.productos.service;

import java.util.List;

import com.springboot.productos.model.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	public Producto findById(Long id);
	public Producto save(Producto producto);
	public void deleteById(Long id);
	
}
