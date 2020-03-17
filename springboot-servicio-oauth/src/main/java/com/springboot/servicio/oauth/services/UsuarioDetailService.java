package com.springboot.servicio.oauth.services;

import com.commons.usuarios.model.Usuario;

public interface UsuarioDetailService {

	public Usuario findByUsername(String username);
}
