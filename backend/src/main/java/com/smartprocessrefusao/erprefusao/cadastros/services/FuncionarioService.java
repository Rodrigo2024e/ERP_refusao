package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.FichaFuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.FuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Setor;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.FuncionarioRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.SetorRepository;
import com.smartprocessrefusao.erprefusao.exceptions.services.DatabaseException;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

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
	
	@Transactional
	public FuncionarioDTO insert(FuncionarioDTO dto) {
		Funcionario entity = new Funcionario();
		copyDtoToEntity(dto, entity);
		entity = funcionarioRepository.save(entity);
		return new FuncionarioDTO(entity);
	}
	
	@Transactional
	public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
		try {
			Funcionario entity = funcionarioRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = funcionarioRepository.save(entity);
			return new FuncionarioDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!funcionarioRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			funcionarioRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(FuncionarioDTO dto, Funcionario entity) {
	    entity.setNomePessoa(dto.getNomePessoa());
	    entity.setEmail(dto.getEmail());
	    entity.setCelular(dto.getCelular());
	    entity.setTelefone(dto.getTelefone());
	    entity.setCpf(dto.getCpf());
	    entity.setRg(dto.getRg());
	    entity.setUsuarioSistema(dto.getUsuarioSistema());
	    
	  //SEM LISTA - 1 FUNCIONARIO PARA 1 SETOR
	    if (dto.getSetorId() != null) {
	    Setor setor = setorRepository.findById(dto.getSetorId())
	        .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
	    entity.setSetor(setor);
	    } else {
	    	entity.setSetor(null);
	    }
		    
	}

	
}
