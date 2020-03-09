package com.springboot.productos.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.productos.model.entity.Producto;



public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
