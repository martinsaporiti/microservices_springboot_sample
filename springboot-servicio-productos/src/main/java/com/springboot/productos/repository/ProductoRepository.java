package com.springboot.productos.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commons.model.Producto;


public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
