package com.ayanokoujifl.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayanokoujifl.cursomc.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
