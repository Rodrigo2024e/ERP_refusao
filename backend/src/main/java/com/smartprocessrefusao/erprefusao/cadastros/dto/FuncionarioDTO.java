package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;

public class FuncionarioDTO {

	private long id;
	private String nomePessoa;
	private String email;
	private String celular;
	private String telefone;
	private String cpf;
	private String rg;
	private boolean usuarioSistema;
	private long setorId;
	private String setorNome;
	private String setorProcesso;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String nomeCidade;
	private String nomeEstado;
	private String nomePais;

	
	public FuncionarioDTO() {
		
	}

	public FuncionarioDTO(long id, String nomePessoa, String email, String celular, String telefone, String cpf,
			String rg, boolean usuarioSistema, long setorId, String setorNome, String setorProcesso, String logradouro,
			Integer numero, String complemento, String bairro, String cep, String nomeCidade, String nomeEstado,
			String nomePais) {
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
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.nomeCidade = nomeCidade;
		this.nomeEstado = nomeEstado;
		this.nomePais = nomePais;
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
		setorNome = entity.getSetor().getsetor();
		setorProcesso = entity.getSetor().getProcesso();
		logradouro = entity.getEndereco().getLogradouro();
		numero = entity.getEndereco().getNumero();
		complemento = entity.getEndereco().getComplemento();
		bairro =  entity.getEndereco().getBairro();
		cep = entity.getEndereco().getCidade().getCep();
		nomeCidade = entity.getEndereco().getCidade().getNomeCidade();
		nomeEstado = entity.getEndereco().getCidade().getEstados().getNomeEstado();
		nomePais =  entity.getEndereco().getCidade().getEstados().getPaises().getNomePais();
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getSetorId() {
		return setorId;
	}

	public void setSetorId(long setorId) {
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

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	
	
}
