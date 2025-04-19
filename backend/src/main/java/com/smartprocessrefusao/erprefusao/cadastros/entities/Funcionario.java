package com.smartprocessrefusao.erprefusao.cadastros.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_funcionario")
public class Funcionario extends Pessoa {
	private static final long serialVersionUID = 1L;

	private String cpf;
	private String rg;
	private Boolean usuarioSistema;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
//	private Endereco enderecos;
	
	public Funcionario() {
		
	}

	public Funcionario(Long id, String nomePessoa, String email, String celular, String telefone,
			Endereco endereco, String cpf, String rg, Boolean usuarioSistema, Setor setor) {
		super(id, nomePessoa, email, celular, telefone, endereco);
		this.cpf = cpf;
		this.rg = rg;
		this.usuarioSistema = usuarioSistema;
		this.setor = setor;
//		this.enderecos = enderecos;
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

	public Boolean getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(Boolean usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
/*
	public Endereco getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Endereco enderecos) {
		this.enderecos = enderecos;
	}
*/	
}
