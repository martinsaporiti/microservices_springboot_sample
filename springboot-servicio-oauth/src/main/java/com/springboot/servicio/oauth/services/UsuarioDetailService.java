package com.springboot.servicio.oauth.services;

import com.springboot.usuarios.commons.model.Usuario;

public interface UsuarioDetailService {

	public Usuario findByUsername(String username);
}
