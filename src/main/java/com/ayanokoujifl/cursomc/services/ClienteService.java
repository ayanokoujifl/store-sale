package com.ayanokoujifl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayanokoujifl.cursomc.dto.ClienteDTO;
import com.ayanokoujifl.cursomc.dto.ClienteNewDTO;
import com.ayanokoujifl.cursomc.entities.Cidade;
import com.ayanokoujifl.cursomc.entities.Cliente;
import com.ayanokoujifl.cursomc.entities.Endereco;
import com.ayanokoujifl.cursomc.entities.enums.TipoCliente;
import com.ayanokoujifl.cursomc.repositories.ClienteRepository;
import com.ayanokoujifl.cursomc.repositories.EnderecoRepository;
import com.ayanokoujifl.cursomc.services.exception.DataIntegrityException;
import com.ayanokoujifl.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	@Autowired
	EnderecoRepository enderecoRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + ", Tipo: " + ClienteService.class.getName()));
	}

	public List<Cliente> findAll() {
		List<Cliente> obj = repository.findAll();
		return obj;
	}

	@Transactional
	public Cliente save(Cliente cliente) {
		cliente.setId(null);
		cliente = repository.saveAndFlush(cliente);
		enderecoRepository.saveAllAndFlush(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente c) {
		Cliente newObj = findById(c.getId());
		updateData(newObj, c);
		return repository.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas!");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	public Cliente fromDto(ClienteNewDTO obj) {
		Cliente c = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getNumeroDocumento(),
				TipoCliente.toEnum(obj.getTipo()));
		Cidade cid = new Cidade();
		cid.setId(obj.getCidadeId());
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(),
				obj.getCep(), c, cid);
		c.getEnderecos().add(end);
		c.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			c.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			c.getTelefones().add(obj.getTelefone3());
		}
		return c;
	}
}
