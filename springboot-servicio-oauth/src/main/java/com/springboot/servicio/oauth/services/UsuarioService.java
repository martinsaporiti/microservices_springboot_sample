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

import com.springboot.servicio.oauth.clients.UsuarioFeignClient;
import com.springboot.usuarios.commons.model.Usuario;

@Service
public class UsuarioService implements UserDetailsService, UsuarioDetailService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = client.findByUsername(username);
		
		// Si el usuario no existe se levanta una excepción:
		if(usuario == null) {
			log.error("No se ha encontrado el usuario para authenticarse: " + username);
			throw new UsernameNotFoundException("No se ha encontrado el usuario para authenticarse: " + username);
		}
		
		
		// Se cargan los permisos del usuario:
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream().map( role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info("Role" + authority.getAuthority()))
				.collect(Collectors.toList()); 
		
		// Se retorna el usuario:
		log.info("Se authenticó el usuario" + username);
		return new User(usuario.getUsername(), 
				usuario.getPassword(), 
				usuario.getEnabled(), 
				true, 
				true, 
				true, 
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}
