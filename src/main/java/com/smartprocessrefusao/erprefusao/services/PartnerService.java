package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.dto.PartnerReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.projections.PartnerReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;

	@Transactional(readOnly = true)
	public List<PartnerDTO> findAll() {
		List<Partner> list = partnerRepository.findAll();
		return list.stream().map(PartnerDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public Page<PartnerReportDTO> reportPartner(String name, Long partnerId, Pageable pageable) {

		Page<PartnerReportProjection> page = partnerRepository.searchPeopleNameByOrId(name, partnerId, pageable);

		return page.map(PartnerReportDTO::new);
	}

	@Transactional(readOnly = true)
	public PartnerDTO findById(Long id) {
		try {
			Optional<Partner> obj = partnerRepository.findById(id);
			Partner entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new PartnerDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

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
		} catch (EntityNotFoundException e) {
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
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(PartnerDTO dto, Partner entity) {
		entity.setName(dto.getName().toUpperCase());
		entity.setEmail(dto.getEmail().toLowerCase());
		entity.setCellPhone(dto.getCellPhone());
		entity.setTelephone(dto.getTelephone());
		entity.setCnpj(dto.getCnpj());
		entity.setIe(dto.getIe());
		entity.setSupplier(dto.getSupplier());
		entity.setClient(dto.getClient());
		entity.setActive(dto.getActive());

	}

}
