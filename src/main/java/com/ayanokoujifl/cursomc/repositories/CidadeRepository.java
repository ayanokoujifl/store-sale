package com.ayanokoujifl.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ayanokoujifl.cursomc.entities.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	@Transactional(readOnly = true)
	@Query("select obj from Cidade obj where obj.estado.id = :estadoId order by obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estadoId);
	
}
