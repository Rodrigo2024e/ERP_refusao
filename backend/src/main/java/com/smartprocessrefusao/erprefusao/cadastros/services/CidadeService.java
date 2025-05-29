package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.CidadeDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.EnderecoDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Cidade;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.CidadeRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.EnderecoRepository;
import com.smartprocessrefusao.erprefusao.enumerados.Estado;
import com.smartprocessrefusao.erprefusao.exceptions.services.DatabaseException;
import com.smartprocessrefusao.erprefusao.exceptions.services.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional(readOnly = true)
	public Page<CidadeDTO> findAllPaged(Pageable pageable) {
		Page<Cidade> list = cidadeRepository.findAll(pageable);
		return list.map(x -> new CidadeDTO(x));
	}

	@Transactional(readOnly = true)
	public CidadeDTO findById(Long id) {
		try {
		Optional<Cidade> obj = cidadeRepository.findById(id);
		Cidade entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new CidadeDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public CidadeDTO insert(CidadeDTO dto) {
		Cidade entity = new Cidade();
		copyDtoToEntity(dto, entity);
		entity = cidadeRepository.save(entity);
		return new CidadeDTO(entity);
	}
	
	@Transactional
	public CidadeDTO update(Long id, CidadeDTO dto) {
		try {
			Cidade entity = cidadeRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = cidadeRepository.save(entity);
			return new CidadeDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!cidadeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			cidadeRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(CidadeDTO dto, Cidade entity) {
		entity.setCidade(dto.getCidade());
	    entity.setCep(dto.getCep());

	    // COM ENUMERADO
	    if (dto.getUfEstado() != null && !dto.getUfEstado().isEmpty()) {
	        Estado estado = Estado.fromUf(dto.getUfEstado()); // converte sigla em enum
	        entity.setEstado(estado);
	        
	    } else {
	        throw new IllegalArgumentException("A UF do estado é obrigatória.");
	    }
	    
	    // COM LISTA NA ENTIDADE
	    entity.getEnderecos().clear();
		for (EnderecoDTO citDto : dto.getEnderecos()) {
			Endereco endereco = enderecoRepository.getReferenceById(citDto.getId());
			entity.getEnderecos().add(endereco);			
		}

	}
	
}
