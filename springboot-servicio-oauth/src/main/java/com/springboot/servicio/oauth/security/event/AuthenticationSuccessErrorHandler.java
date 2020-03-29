package com.springboot.servicio.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.commons.usuarios.model.Usuario;
import com.springboot.servicio.oauth.services.UsuarioDetailService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log =  LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private UsuarioDetailService usuarioService;
	
	@Autowired
	private Tracer tracer;
	
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Success Login: " + user.getUsername());
		
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		
		// Validamos al cantidad de intentos previos, si es mayor a 0 => lo volvemos a 0.
		 
		if(usuario.getIntentos() == null || usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		
		
		String mensaje = "Error Login: " + exception.getMessage();
		log.error(mensaje);
		
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			
			if(usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			
			log.info(String.format("Intentos actuales del usuario %s : %d", 
					authentication.getName(), 
					usuario.getIntentos()));
			
			errors.append(" - " + String.format("Intentos actuales del usuario %s : %d", 
					authentication.getName(), 
					usuario.getIntentos()));
			
			if(usuario.getIntentos() >= 3) {
				
				log.error(String.format("El usuario %s deshabilitado", authentication.getName()));
				errors.append(" - " + String.format("El usuario %s deshabilitado", authentication.getName()));
				
				usuario.setEnabled(false);
			}
			
			tracer.currentSpan().tag("erros.mensaje", errors.toString());
			
			// Actualizamos al usuario:
			usuarioService.update(usuario, usuario.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
	}
	
	
}
