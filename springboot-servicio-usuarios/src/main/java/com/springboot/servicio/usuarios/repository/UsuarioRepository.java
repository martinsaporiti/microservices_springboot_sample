package com.springboot.servicio.usuarios.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import com.commons.usuarios.model.Usuario;





@RepositoryRestResource(path="usuarios")
@Transactional
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	@RestResource(path = "buscar-username")
	public Usuario findByUsername(@Param("username") String username);
	
	
	// Otra alternativa al método anterior es:
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);
	
}
