package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.ParceiroDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Parceiro;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.EnderecoRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.ParceiroRepository;
import com.smartprocessrefusao.erprefusao.exceptions.services.DatabaseException;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ParceiroService {

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional(readOnly = true)
	public Page<ParceiroDTO> findAllPaged(Pageable pageable) {
		Page<Parceiro> list = parceiroRepository.findAll(pageable);
		return list.map(x -> new ParceiroDTO(x));
	}

	@Transactional(readOnly = true)
	public ParceiroDTO findById(Long id) {
		try {
		Optional<Parceiro> obj = parceiroRepository.findById(id);
		Parceiro entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new ParceiroDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public ParceiroDTO insert(ParceiroDTO dto) {
		Parceiro entity = new Parceiro();
		copyDtoToEntity(dto, entity);
		entity = parceiroRepository.save(entity);
		return new ParceiroDTO(entity);
	}
	
	@Transactional
	public ParceiroDTO update(Long id, ParceiroDTO dto) {
		try {
			Parceiro entity = parceiroRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = parceiroRepository.save(entity);
			return new ParceiroDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!parceiroRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			parceiroRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(ParceiroDTO dto, Parceiro entity) {
	    entity.setCnpj(dto.getCnpj());
	    entity.setIe(dto.getIe());
	    entity.setFornecedor(dto.isFornecedor());
	    entity.setCliente(dto.isCliente());
	    entity.setAtivo(dto.isAtivo());
	   
	    if (dto.getEnderecoId() != null) {
	        Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
	            .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
	        entity.setEndereco(endereco);
	    } else {
	        entity.setEndereco(null); 
	    }
	}
	
}
