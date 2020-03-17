package com.springboot.servicio.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.commons.usuarios.model.Usuario;
import com.springboot.servicio.oauth.services.UsuarioDetailService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	@Autowired
	private UsuarioDetailService usuarioDetailService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> info = new HashMap<String, Object>();
		
		Usuario usuario = usuarioDetailService.findByUsername(authentication.getName());
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("email", usuario.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

	
}
