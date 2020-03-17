package com.springboot.servicio.usuarios.repository;

import org.springframework.data.repository.CrudRepository;

import com.commons.usuarios.model.Usuario;


public interface UsuarioRepoTest extends CrudRepository<Usuario, Long>{

}
