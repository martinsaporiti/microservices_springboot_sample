package com.springboot.servicio.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.commons.usuarios.model.Usuario;
import com.springboot.servicio.oauth.clients.UsuarioFeignClient;

import brave.Tracer;
import feign.FeignException;

@Service
public class UsuarioService implements UserDetailsService, UsuarioDetailService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient client;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		try {
			
			Usuario usuario = client.findByUsername(username);
			
			// Se cargan los permisos del usuario:
			List<GrantedAuthority> authorities = usuario.getRoles()
					.stream().map( role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(authority -> log.info("Role" + authority.getAuthority()))
					.collect(Collectors.toList()); 
			
			// Se retorna el usuario:
			
			String mensaje = "Se authenticó el usuario: " + username;
			log.info("\n" + mensaje);
			tracer.currentSpan().tag("success.mensaje", mensaje);
			return new User(usuario.getUsername(), 
					usuario.getPassword(), 
					usuario.getEnabled(), 
					true, 
					true, 
					true, 
					authorities);
		} catch (FeignException e) {
			
			log.error(e.getMessage());
			log.error(e.getCause().getMessage());
			
			
			String mensajeError = "No se ha encontrado el usuario para authenticarse: " + username;
			tracer.currentSpan().tag("errors.mensaje", mensajeError + ":" + e.getMessage());
			
			// Si el usuario no existe se levanta una excepción:
			throw new UsernameNotFoundException(mensajeError);
		}

	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
