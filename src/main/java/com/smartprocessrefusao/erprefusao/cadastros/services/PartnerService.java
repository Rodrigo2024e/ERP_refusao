package com.smartprocessrefusao.erprefusao.cadastros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.ReportPartnerDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Partner;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.projections.ReportPartnerProjection;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;

	  @Transactional(readOnly = true) 
	    public Page<ReportPartnerDTO> reportPartner(String name, Long partnerId, Pageable pageable) {

	        Page<ReportPartnerProjection> page = partnerRepository.searchPeopleNameByOrId(name, partnerId, pageable);

	        return page.map(ReportPartnerDTO::new);
	    }
	
	
	@Transactional
	public PartnerDTO insert(PartnerDTO dto) {
		Partner entity = new Partner();
		copyDtoToEntity(dto, entity);
		entity = partnerRepository.save(entity);
		return new PartnerDTO(entity);
	}
	
	@Transactional
	public PartnerDTO update(Long id, PartnerDTO dto) {
		try {
			Partner entity = partnerRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = partnerRepository.save(entity);
			return new PartnerDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!partnerRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			partnerRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(PartnerDTO dto, Partner entity) {
		entity.setname(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setCellPhone(dto.getCellPhone());
		entity.setTelephone(dto.getTelephone());
		entity.setCnpj(dto.getCnpj());
	    entity.setIe(dto.getIe());
	    entity.setSupplier(dto.getSupplier());
	    entity.setClient(dto.getClient());
	    entity.setActive(dto.getActive());
	    
	}
	
}
