package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

	private Long id;
	
	@Size(min = 3, max = 30, message = "O nome deve ter entre 5 a 30 caracteres")
	@NotBlank(message = "O campo logradouro é obrigatório")
	private String logradouro;
	
	@NotNull(message = "O numero é obrigatório")
	private Integer numero;
	
	private String complemento;
	
	@Size(min = 5, max = 50, message = "O nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "O campo bairro é obrigatório")
	private String bairro;
	
	private Long cidadeId;
	private String nomeCidade;
	private String estado;
	private String uf;
	
	private Long pessoaId;
	
	
	public EnderecoDTO() {
		
	}

	public EnderecoDTO(Long id, String logradouro, Integer numero, String complemento, String bairro, 
			Long cidadeId, String nomeCidade, String estado, String uf, Long pessoaId) {
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidadeId = cidadeId;
		this.nomeCidade = nomeCidade;
		this.estado = estado;
		this.uf = uf;
		this.pessoaId = pessoaId;
	}
	
	public EnderecoDTO(Endereco entity) {
		id = entity.getId();
		logradouro = entity.getLogradouro();
		numero = entity.getNumero();
		complemento = entity.getComplemento();
		bairro = entity.getBairro();
		
		if (entity.getCidades().getId() != null) {
			cidadeId = entity.getCidades().getId();
			nomeCidade = entity.getCidades().getCidade();
		}
		
		if(entity.getCidades().getEstado().getUf() != null) {
			uf = entity.getCidades().getEstado().getUf();
			estado = entity.getCidades().getEstado().getNome();
		}
		
		if(entity.getPessoa().getId() != null) {
			pessoaId = entity.getPessoa().getId();
		}
	}

	public Long getId() {
		return id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public Long getCidadeId() {
		return cidadeId;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public String getEstado() {
		return estado;
	}

	public String getUf() {
		return uf;
	}

	public Long getPessoaId() {
		return pessoaId;
	}


}
