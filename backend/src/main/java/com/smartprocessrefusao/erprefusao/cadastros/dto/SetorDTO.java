package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Setor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SetorDTO {
	
	private Long id;
	
	@Size(min = 3, max = 20, message = "O nome do setor deve ter entre 3 a 20 caracteres")
	@NotBlank(message = "O campo setor é obrigatório")
	private String setorNome;
	
	@Size(min = 3, max = 20, message = "O nome do processo deve ter entre 3 a 20 caracteres")
	@NotBlank(message = "O campo processo é obrigatório")
	private String processo;
	
	public SetorDTO() {
		
	}

	public SetorDTO(Long id, String setorNome, String processo) {
		this.id = id;
		this.setorNome = setorNome;
		this.processo = processo;
	}

	public SetorDTO(Setor entity) {
		id = entity.getId();
		setorNome = entity.getSetorNome();
		processo = entity.getProcesso();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSetorNome() {
		return setorNome;
	}

	public void setSetorNome(String setorNome) {
		this.setorNome= setorNome;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}
	
	
	
	
}
