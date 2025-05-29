package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.EmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.ReportEmployeeDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Employee;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Sector;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeProjection;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SectorRepository sectorRepository;
	 
	   @Transactional(readOnly = true) 
	    public Page<ReportEmployeeDTO> reportEmployee(String name, Long peopleId, Pageable pageable) {

	        Page<ReportEmployeeProjection> page = employeeRepository.searchPeopleNameByOrId(name, peopleId, pageable);

	        return page.map(ReportEmployeeDTO::new);
	    }
	 
	@Transactional(readOnly = true)
	public Page<EmployeeSectorDTO> EmployeesBySector(Long sectorId, String name, Pageable pageable) {
		
		Sector sector = (sectorId == 0) ? null:
			sectorRepository.getReferenceById(sectorId);
		Page<Employee> page = employeeRepository.searchBySector(sector, name, pageable);
		
		return page.map(x ->  new EmployeeSectorDTO(x));
	}
	
	@Transactional(readOnly = true)
	public EmployeeDTO findById(Long id) {
		try {
		Optional<Employee> obj = employeeRepository.findById(id);
		Employee entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new EmployeeDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee entity = new Employee();
		copyDtoToEntity(dto, entity);
		entity = employeeRepository.save(entity);
		return new EmployeeDTO(entity);
	}
	
	@Transactional
	public EmployeeDTO update(Long id, EmployeeDTO dto) {
		try {
			Employee entity = employeeRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = employeeRepository.save(entity);
			return new EmployeeDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			employeeRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(EmployeeDTO dto, Employee entity) {
	    entity.setname(dto.getName());
	    entity.setEmail(dto.getEmail());
	    entity.setCellPhone(dto.getCellPhone());
	    entity.setTelephone(dto.getTelephone());
	    entity.setCpf(dto.getCpf());
	    entity.setRg(dto.getRg());
	    entity.setSysUser(dto.getSysUser());
	    
	  //SEM LISTA - 1 FUNCIONARIO PARA 1 SETOR
	    if (dto.getSectorId() != null) {
	    Sector setor = sectorRepository.findById(dto.getSectorId())
	        .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
	    entity.setSector(setor);
	    } else {
	    	entity.setSector(null);
	    }
	}
}
	

