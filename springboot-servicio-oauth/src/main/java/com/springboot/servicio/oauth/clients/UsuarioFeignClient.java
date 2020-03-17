package com.springboot.servicio.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.commons.usuarios.model.Usuario;

@FeignClient("servicio-usuarios")
public interface UsuarioFeignClient {
	
	@GetMapping("/usuarios/search/obtenerPorUsername")
	public Usuario findByUsername(@RequestParam String username);
	
}
