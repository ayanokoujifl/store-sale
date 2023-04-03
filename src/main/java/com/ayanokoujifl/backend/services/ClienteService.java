package com.ayanokoujifl.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayanokoujifl.backend.dto.ClienteDTO;
import com.ayanokoujifl.backend.dto.ClienteNewDTO;
import com.ayanokoujifl.backend.entities.Cidade;
import com.ayanokoujifl.backend.entities.Cliente;
import com.ayanokoujifl.backend.entities.Endereco;
import com.ayanokoujifl.backend.entities.enums.Perfil;
import com.ayanokoujifl.backend.entities.enums.TipoCliente;
import com.ayanokoujifl.backend.repositories.ClienteRepository;
import com.ayanokoujifl.backend.repositories.EnderecoRepository;
import com.ayanokoujifl.backend.security.UserSS;
import com.ayanokoujifl.backend.services.exceptions.AuthorizationException;
import com.ayanokoujifl.backend.services.exceptions.DataIntegrityException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	BCryptPasswordEncoder be;

	public Cliente findById(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new com.ayanokoujifl.backend.services.exceptions.ObjectNotFoundException(
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
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null, null);
	}

	public Cliente fromDto(ClienteNewDTO obj) {
		Cliente c = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getNumeroDocumento(),
				TipoCliente.toEnum(obj.getTipo()), be.encode(obj.getSenha()));
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

	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Cliente obj = repository.findByEmail(email);
		if(obj==null) {
			throw new com.ayanokoujifl.backend.services.exceptions.ObjectNotFoundException(
					"Cliente não encontrado!\nId: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
}
