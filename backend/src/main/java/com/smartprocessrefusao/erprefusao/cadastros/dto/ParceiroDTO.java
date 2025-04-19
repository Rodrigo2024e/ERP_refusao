package com.smartprocessrefusao.erprefusao.cadastros.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Parceiro;
import com.smartprocessrefusao.erprefusao.enumerados.Estado;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ParceiroDTO {

	private Long id;
	
	@Size(min = 5, max = 50, message = "O campo nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "O campo nome é obrigatório")
	private String nomePessoa;
	
	@NotEmpty(message = "Informe um e-mail válido")
    @Email
	private String email;
	
	@NotBlank(message = "O celular é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{5}-\\d{4}", message = "O celular deve estar no formato 00-00000-0000")
	private String celular;
	
	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{4}-\\d{4}", message = "O telefone fixo deve estar no formato 00-0000-0000")
	private String telefone;
	
	@NotBlank(message = "O campo CPF é obrigatório")
	@CNPJ
	private String cnpj;
	
	private String ie;
	
	@NotNull(message = "Informe se o parceiro é um fornecedor")
	private Boolean fornecedor;
	
	@NotNull(message = "Informe se o parceiro é um cliente")
	private Boolean cliente;
	
	@NotNull(message = "Informe se o parceiro está ativo no sistema")
	private Boolean ativo;
	
	@NotNull(message = "O ID do endereço é obrigatório")
	private Long enderecoId;
	
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String nomeCidade;
	
	private Estado nomeEstado;
	
	public ParceiroDTO() {
		
	}

	public ParceiroDTO(Long id, String nomePessoa, String email, String celular,  String telefone, String cnpj, 
			String ie, Boolean fornecedor, Boolean cliente, Boolean ativo, Long enderecoId, String logradouro, Integer numero,
			String complemento, String bairro, String cep, String nomeCidade, Estado nomeEstado) {
		this.id = id;
		this.nomePessoa = nomePessoa;
		this.email = email;
		this.celular = celular;
		this.telefone = telefone;
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
		nomePessoa = entity.getNomePessoa();
		email = entity.getEmail();
		celular = entity.getCelular();
		telefone = entity.getTelefone();
		cnpj = entity.getCnpj();
		ie = entity.getIe();
		fornecedor = entity.getFornecedor();
		cliente = entity.getCliente();
		ativo = entity.getAtivo();
		
		if (entity.getEndereco() != null) {
			enderecoId = entity.getEndereco().getId();
			logradouro = entity.getEndereco().getLogradouro();
			numero = entity.getEndereco().getNumero();
			complemento = entity.getEndereco().getComplemento();
			bairro = entity.getEndereco().getBairro();
			cep = entity.getEndereco().getCidades().getCep();
			nomeCidade = entity.getEndereco().getCidades().getCidade();
			nomeEstado = entity.getEndereco().getCidades().getEstado();
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Boolean fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Boolean getCliente() {
		return cliente;
	}

	public void setCliente(Boolean cliente) {
		this.cliente = cliente;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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

	public Estado getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(Estado nomeEstado) {
		this.nomeEstado = nomeEstado;
	}



	
}
