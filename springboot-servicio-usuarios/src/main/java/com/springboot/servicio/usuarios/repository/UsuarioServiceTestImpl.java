package com.springboot.servicio.usuarios.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.usuarios.commons.model.Usuario;


@Service
public class UsuarioServiceTestImpl implements UsuarioServiceTest{

	private UsuarioRepoTest repo;
	
	
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) repo.findAll();
	}

}
