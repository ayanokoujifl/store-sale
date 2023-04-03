package com.ayanokoujifl.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayanokoujifl.backend.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	public List<Estado> findAllByOrderByNome();
	
}
