package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Setor;

public class SetorDTO {
	
	private long id;
	private String setor;
	private String processo;
	
	public SetorDTO() {
		
	}

	public SetorDTO(long id, String setor, String processo) {
		this.id = id;
		this.setor = setor;
		this.processo = processo;
	}

	public SetorDTO(Setor entity) {
		id = entity.getId();
		setor = entity.getsetor();
		processo = entity.getProcesso();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}
	
	
	
	
}
