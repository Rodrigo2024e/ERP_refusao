package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

	private Long id;
	
	@Size(min = 5, max = 100, message = "O nome deve ter entre 5 a 100 caracteres")
	@NotBlank(message = "O campo logradouro é obrigatório")
	private String logradouro;
	
	@NotBlank(message = "O numero é obrigatório")
	@Pattern(regexp = "\\d+", message = "O número do endereço deve conter apenas dígitos")
	private Integer numero;
	
	private String complemento;
	
	@Size(min = 5, max = 50, message = "O nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "O campo bairro é obrigatório")
	private String bairro;
	
	private CidadeDTO cidade;
	
	public EnderecoDTO() {
		
	}

	public EnderecoDTO(Long id, String logradouro, Integer numero, String complemento, String bairro) {
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
	}
	
	public EnderecoDTO(Endereco entity) {
		id = entity.getId();
		logradouro = entity.getLogradouro();
		numero = entity.getNumero();
		complemento = entity.getComplemento();
		bairro = entity.getBairro();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidade) {
		this.cidade = cidade;
	}
	
	
}
