package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Departament;

import jakarta.validation.constraints.Size;

public class DepartamentDTO {
	
	private Long id;
	
	@Size(min = 3, max = 20, message = "O nome do setor deve ter entre 3 a 15 caracteres")
	private String name;
	
	@Size(min = 3, max = 20, message = "O nome do processo deve ter entre 3 a 16 caracteres")
	private String process;
	
	public DepartamentDTO() {
		
	}
	
	public DepartamentDTO(Long id, String name, String process) {
		this.id = id;
		this.name = name;
		this.process = process;
	}


	public DepartamentDTO(Departament entity) {
		id = entity.getId();
		name = entity.getName();
		process = entity.getProcess();
		
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProcess() {
		return process;
	}



}
