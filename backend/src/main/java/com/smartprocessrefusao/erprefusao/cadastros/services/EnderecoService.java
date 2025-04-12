package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.EnderecoDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Cidade;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.CidadeRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.EnderecoRepository;
import com.smartprocessrefusao.erprefusao.exceptions.services.DatabaseException;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional(readOnly = true)
	public Page<EnderecoDTO> findAllPaged(Pageable pageable) {
		Page<Endereco> list = enderecoRepository.findAll(pageable);
		return list.map(x -> new EnderecoDTO(x));
	}

	@Transactional(readOnly = true)
	public EnderecoDTO findById(Long id) {
		try {
		Optional<Endereco> obj = enderecoRepository.findById(id);
		Endereco entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new EnderecoDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public EnderecoDTO insert(EnderecoDTO dto) {
		Endereco entity = new Endereco();
		copyDtoToEntity(dto, entity);
		entity = enderecoRepository.save(entity);
		return new EnderecoDTO(entity);
	}
	
	@Transactional
	public EnderecoDTO update(Long id, EnderecoDTO dto) {
		try {
			Endereco entity = enderecoRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = enderecoRepository.save(entity);
			return new EnderecoDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!enderecoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			enderecoRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(EnderecoDTO dto, Endereco entity) {
	    entity.setLogradouro(dto.getLogradouro());
	    entity.setNumero(dto.getNumero());
	    entity.setComplemento(dto.getComplemento());
	    entity.setBairro(dto.getComplemento());
	
	    if (dto.getCidade() != null) {
		    Cidade cidade = cidadeRepository.findById(dto.getCidade().getId())
		        .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrado"));
		    entity.setCidade(cidade);
		    } else {
		    	entity.setCidade(null);
		    }
	}
}
