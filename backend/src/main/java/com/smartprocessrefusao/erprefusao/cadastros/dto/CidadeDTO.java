package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Cidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CidadeDTO {

	private Long id;
	
	@Size(min = 5, max = 100, message = "O nome deve ter entre 5 a 100 caracteres")
	@NotBlank(message = "O campo nome da cidade é obrigatório")
	private String nomeCidade;
	
	@NotBlank(message = "O campo CEP é obrigatório")
	@Pattern(regexp = "\\d{2}.\\d{3}-\\d{3}", message = "O CEP deve estar no formato 00.000-000.")
	private String cep;
	
	private int estadoId;
	
	@NotBlank(message = "O campo UF do Estado é obrigatório")
	private String estadoUf;
	
	private String estadoNome;
	private String pais;
	
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Long id, String nomeCidade, String cep, int estadoId, String estadoUf, String estadoNome, String pais) {
		this.id = id;
		this.nomeCidade = nomeCidade;
		this.cep = cep;
		this.estadoId = estadoId;
		this.estadoUf = estadoUf;
		this.estadoNome = estadoNome;
		this.pais = pais;

		
	}
	
	public CidadeDTO(Cidade entity) {
		id = entity.getId();
		nomeCidade = entity.getNomeCidade();
		cep = entity.getCep();
	    estadoId = entity.getEstado().getId();
		estadoUf = entity.getEstado().getUf();
		estadoNome = entity.getEstado().getNome();
		pais = entity.getEstado().getPais();	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public int getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(int estadoId) {
		this.estadoId = estadoId;
	}

	public String getEstadoUf() {
		return estadoUf;
	}

	public void setEstadoUf(String estadoUf) {
		this.estadoUf = estadoUf;
	}

	public String getEstadoNome() {
		return estadoNome;
	}

	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
}
