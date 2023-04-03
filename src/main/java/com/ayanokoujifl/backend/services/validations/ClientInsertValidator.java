package com.ayanokoujifl.backend.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ayanokoujifl.backend.controllers.exception.FieldMessage;
import com.ayanokoujifl.backend.dto.ClienteNewDTO;
import com.ayanokoujifl.backend.entities.Cliente;
import com.ayanokoujifl.backend.entities.enums.TipoCliente;
import com.ayanokoujifl.backend.repositories.ClienteRepository;
import com.ayanokoujifl.backend.services.validations.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getNumeroDocumento())) {
			list.add(new FieldMessage("numeroDocumento", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidCNPJ(objDto.getNumeroDocumento())) {
			list.add(new FieldMessage("numeroDocumento", "CNPJ inválido"));
		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux!=null) {
		list.add(new FieldMessage("email","Email já existente"));	
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}