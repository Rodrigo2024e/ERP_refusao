package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.FuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.FuncionarioRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.SetorRepository;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Transactional(readOnly = true)
	public Page<FuncionarioDTO> findAllPaged(Pageable pageable) {
		Page<Funcionario> list = funcionarioRepository.findAll(pageable);
		return list.map(x -> new FuncionarioDTO(x));
	}

	@Transactional(readOnly = true)
	public FuncionarioDTO findById(Long id) {
		try {
		Optional<Funcionario> obj = funcionarioRepository.findById(id);
		Funcionario entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new FuncionarioDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
}
