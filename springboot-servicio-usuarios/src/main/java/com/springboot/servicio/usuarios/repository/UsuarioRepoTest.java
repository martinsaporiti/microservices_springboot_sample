package com.springboot.servicio.usuarios.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.usuarios.commons.model.Usuario;


public interface UsuarioRepoTest extends CrudRepository<Usuario, Long>{

}
