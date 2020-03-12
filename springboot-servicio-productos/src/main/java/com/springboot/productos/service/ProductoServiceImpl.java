/**
 * 
 */
package com.springboot.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commons.model.Producto;
import com.springboot.productos.repository.ProductoRepository;

/**
 * @author martinsaporiti
 *
 */
@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return repository.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

}
