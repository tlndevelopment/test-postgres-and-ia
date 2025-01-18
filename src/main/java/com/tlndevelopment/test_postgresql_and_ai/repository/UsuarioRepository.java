package com.tlndevelopment.test_postgresql_and_ai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tlndevelopment.test_postgresql_and_ai.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("select u from Usuario u where u.login = :login")
	Optional<Usuario> findByLogin(@Param("login") String login);
	
	@Query("select u from Usuario u where u.email = :email")
	Optional<Usuario> findByEmail(@Param("email") String email);
	
	@Query("select u from Usuario u join fetch u.filmes f "
			+ "where u.id = :idUsuario and f.id = :idFilme")
	Optional<Usuario> getUserInMovie(@Param("idUsuario") Integer idUsuario, @Param("idFilme") Integer idFilme);
}
