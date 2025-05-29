package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.FichaFuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.FuncionarioRepository;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FichaFuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Transactional(readOnly = true)
	public Page<FichaFuncionarioDTO> findAllPaged(Pageable pageable) {
		Page<Funcionario> list = funcionarioRepository.findAll(pageable);
		return list.map(x -> new FichaFuncionarioDTO(x));
	}
	
	@Transactional(readOnly = true)
	public FichaFuncionarioDTO findById(Long id) {
		try {
		Optional<Funcionario> obj = funcionarioRepository.findById(id);
		Funcionario entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new FichaFuncionarioDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
}
