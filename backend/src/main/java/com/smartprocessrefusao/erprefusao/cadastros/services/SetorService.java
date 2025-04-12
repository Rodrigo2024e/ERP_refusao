package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.SetorDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Setor;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.FuncionarioRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.SetorRepository;
import com.smartprocessrefusao.erprefusao.exceptions.services.DatabaseException;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Transactional(readOnly = true)
	public Page<SetorDTO> findAllPaged(Pageable pageable) {
		Page<Setor> list = setorRepository.findAll(pageable);
		return list.map(x -> new SetorDTO(x));
	}

	@Transactional(readOnly = true)
	public SetorDTO findById(Long id) {
		try {
		Optional<Setor> obj = setorRepository.findById(id);
		Setor entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new SetorDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public SetorDTO insert(SetorDTO dto) {
		Setor entity = new Setor();
		copyDtoToEntity(dto, entity);
		entity = setorRepository.save(entity);
		return new SetorDTO(entity);
	}
	
	@Transactional
	public SetorDTO update(Long id, SetorDTO dto) {
		try {
			Setor entity = setorRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = setorRepository.save(entity);
			return new SetorDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!setorRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			setorRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(SetorDTO dto, Setor entity) {
	    entity.setSetorNome(dto.getSetorNome());
	    entity.setProcesso(dto.getProcesso());

	    if (dto.getFuncionarios() != null) {
	    Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarios().get(0).getId())
	        .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
	    entity.adicionarFuncionario(funcionario);
	    } else {
	    	entity.setFuncionarios(null);
	    }   

	}
}
