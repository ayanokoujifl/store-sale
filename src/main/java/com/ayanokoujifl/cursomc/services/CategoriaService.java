package com.ayanokoujifl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.cursomc.dto.CategoriaDTO;
import com.ayanokoujifl.cursomc.entities.Categoria;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;
import com.ayanokoujifl.cursomc.services.exception.DataIntegrityException;
import com.ayanokoujifl.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + CategoriaService.class.getName()));
	}

	public List<Categoria> findAll() {
		List<Categoria> obj = repository.findAll();
		return obj;
	}

	public Categoria save(Categoria categoria) {
		categoria.setId(null);
		return repository.saveAndFlush(categoria);
	}

	public Categoria update(Categoria c) {
		findById(c.getId());
		return repository.save(c);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Uma categoria com produtos não pode ser excluída!");
		}
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO obj) {
		return new Categoria(obj.getId(),obj.getNome());
	}
}
