package com.smartprocessrefusao.erprefusao.cadastros.dto;

import java.util.Objects;

import org.hibernate.validator.constraints.br.CNPJ;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Parceiro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParceiroDTO {

	private Long id;
	
	@NotBlank(message = "O campo CPF é obrigatório")
	@CNPJ(message = "CNPJ deve ser no formato, 00.000.000/0000-00")
	private String cnpj;
	
	private String ie;
	
	@NotNull(message = "Informe se o parceiro é um fornecedor")
	private boolean fornecedor;
	
	@NotNull(message = "Informe se o parceiro é um cliente")
	private boolean cliente;
	
	@NotNull(message = "Informe se o parceiro está ativo no sistema")
	private boolean ativo;
	
	@NotBlank(message = "Informe o endereço do parceiro")
	private Long enderecoId;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String nomeCidade;
	private String nomeEstado;
	
	public ParceiroDTO() {
		
	}

	public ParceiroDTO(Long id, String cnpj, String ie, boolean fornecedor, boolean cliente, boolean ativo, Long enderecoId,
			String logradouro, Integer numero, String complemento, String bairro, String cep, String nomeCidade,
			String nomeEstado) {
		this.id = id;
		this.cnpj = cnpj;
		this.ie = ie;
		this.fornecedor = fornecedor;
		this.cliente = cliente;
		this.ativo = ativo;
		this.enderecoId = enderecoId;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.nomeCidade = nomeCidade;
		this.nomeEstado = nomeEstado;
	}
	
	public ParceiroDTO(Parceiro entity) {
		id = entity.getId();
		cnpj = entity.getCnpj();
		ie = entity.getIe();
		fornecedor = entity.isFornecedor();
		cliente = entity.isCliente();
		ativo = entity.isAtivo();
		enderecoId = entity.getEndereco().getId();
		logradouro = entity.getEndereco().getLogradouro();
		numero = entity.getEndereco().getNumero();
		complemento = entity.getEndereco().getComplemento();
		bairro = entity.getEndereco().getBairro();
		cep = entity.getEndereco().getCidade().getCep();
		nomeCidade = entity.getEndereco().getCidade().getNomeCidade();
		nomeEstado = entity.getEndereco().getCidade().getEstado().getNome();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public boolean isFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(boolean fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Long getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParceiroDTO other = (ParceiroDTO) obj;
		return Objects.equals(cnpj, other.cnpj);
	}
	
	
}
