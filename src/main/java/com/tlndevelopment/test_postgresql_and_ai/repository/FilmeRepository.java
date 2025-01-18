package com.tlndevelopment.test_postgresql_and_ai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tlndevelopment.test_postgresql_and_ai.domain.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
	@Query("select f from Filme f where f.titulo = :titulo")
	Optional<Filme> findByTitle(@Param("titulo") String titulo);
	
	@Query("select f from Filme f join fetch f.usuarios u "
			+ "where u.id = :idUsuario and f.id = :idFilme")
	Optional<Filme> getMovieInUser(@Param("idUsuario") Integer idUsuario, @Param("idFilme") Integer idFilme);
}
