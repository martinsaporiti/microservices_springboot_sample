package com.springboot.servicio.usuarios.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.usuarios.model.Usuario;


@RestController()
@RequestMapping("/usuarioTest")
public class UsuarioControllerTest {
	
	
	private UsuarioServiceTest service;
	
	
	@GetMapping("/list")
	public List<Usuario> findAll() {
		return service.findAll();
	}

	
	
}
