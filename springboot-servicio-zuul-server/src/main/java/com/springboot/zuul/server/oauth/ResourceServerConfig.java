package com.springboot.zuul.server.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return tokenConverter;
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		// Las rutas específicas van primero, luego van las genéricas:
		
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/products/list", "/api/items/list", "/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, "/api/productos/products/view/{id}", "/api/items/{id}/cantidad/{cantidad}", "/api/usuarios/usuarios/{id}")
		.hasAnyRole("ADMIN", "USER")
		.antMatchers("/api/productos/products/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated();

//		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/productos/listar", "/api/items/listar", "/api/usuarios/usuarios").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/productos/ver/{id}", 
//				"/api/items/ver/{id}/cantidad/{cantidad}", 
//				"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
//		.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
//		.anyRequest().authenticated();
	}
	

}
