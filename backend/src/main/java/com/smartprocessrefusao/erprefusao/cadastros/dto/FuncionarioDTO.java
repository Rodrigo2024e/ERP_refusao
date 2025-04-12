package com.smartprocessrefusao.erprefusao.cadastros.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FuncionarioDTO {

	private Long id;
	
	@Size(min = 5, max = 50, message = "O campo nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "O campo nome é obrigatório")
	private String nomePessoa;
	
	@NotEmpty(message = "Informe um e-mail válido")
    @Email
	private String email;
	
	@NotBlank(message = "O celular é obrigatório")
	@Pattern(regexp = "^\\d{2}9\\d{8}$", message = "O celular deve conter DDD e 9 dígitos, ex: 1199998888")
	private String celular;
	
	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "^\\d{10}$",
			message = "O tlefone deve conter DDD e 10 dígitos, ex: 119999888")
	private String telefone;
	
	@NotBlank(message = "O campo CPF é obrigatório")
	@CPF(message = "CPF deve ser no formato, 00.000.000-00")
	private String cpf;
	
	@NotBlank(message = "O campo RG é obrigatório")
	@Pattern(regexp = "\\d{2}.\\d{3}.\\d{3}-\\d{2}", message = "O RG deve estar no formato 00.000.000-00")
	private String rg;
	
	@NotNull(message = "Informe se o funcionário é usuário do sistema")
	private boolean usuarioSistema;
	
	@NotNull(message = "Informe o setor do funcionário")
	private Long setorId;
	private String setorNome;
	private String setorProcesso;
	
	@NotBlank(message = "Informe o endereço do funcionário")
	private Long enderecoId;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String nomeCidade;
	private String nomeEstado;

	public FuncionarioDTO() {
	}

	public FuncionarioDTO(Long id, String nomePessoa, String email, String celular, String telefone, String cpf,
			String rg, boolean usuarioSistema, Long setorId, String setorNome, String setorProcesso, Long enderecoId,
			String logradouro, Integer numero, String complemento, String bairro, String cep, String nomeCidade,
			String nomeEstado) {
		this.id = id;
		this.nomePessoa = nomePessoa;
		this.email = email;
		this.celular = celular;
		this.telefone = telefone;
		this.cpf = cpf;
		this.rg = rg;
		this.usuarioSistema = usuarioSistema;
		this.setorId = setorId;
		this.setorNome = setorNome;
		this.setorProcesso = setorProcesso;
		this.enderecoId = enderecoId;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.nomeCidade = nomeCidade;
		this.nomeEstado = nomeEstado;
	
	}

	public FuncionarioDTO(Funcionario entity) {
		id = entity.getId();
		nomePessoa = entity.getNomePessoa();
		email = entity.getEmail();
		celular = entity.getCelular();
		telefone = entity.getTelefone();
		cpf = entity.getCpf();
		rg = entity.getRg();
		usuarioSistema = entity.isUsuarioSistema();
		setorId = entity.getSetor().getId();
		setorNome = entity.getSetor().getSetorNome();
		setorProcesso = entity.getSetor().getProcesso();
		enderecoId = entity.getEndereco().getId();
		logradouro = entity.getEndereco().getLogradouro();
		numero = entity.getEndereco().getNumero();
		complemento = entity.getEndereco().getComplemento();
		bairro = entity.getEndereco().getBairro();
		cep = entity.getEndereco().getCidade().getCep();
		nomeCidade = entity.getEndereco().getCidade().getNomeCidade();
		nomeEstado = entity.getEndereco().getCidade().getEstado().getNome();	
	}

	// Getters e Setters

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public boolean isUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(boolean usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public Long getSetorId() {
		return setorId;
	}

	public void setSetorId(Long setorId) {
		this.setorId = setorId;
	}

	public String getSetorNome() {
		return setorNome;
	}

	public void setSetorNome(String setorNome) {
		this.setorNome = setorNome;
	}

	public String getSetorProcesso() {
		return setorProcesso;
	}

	public void setSetorProcesso(String setorProcesso) {
		this.setorProcesso = setorProcesso;
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

	
}

