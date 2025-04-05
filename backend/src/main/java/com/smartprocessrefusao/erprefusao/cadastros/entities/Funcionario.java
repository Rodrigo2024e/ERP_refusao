package com.smartprocessrefusao.erprefusao.cadastros.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_funcionario")
@PrimaryKeyJoinColumn(name = "id")
public class Funcionario extends Pessoa {
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private String rg;
	private boolean usuarioSistema;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	
	public Funcionario() {
		
	}

	public Funcionario(long id, String nome, String email, String celular, String telefone, Endereco endereco,
			String cpf, String rg, boolean usuarioSistema) {
		super(id, nome, email, celular, telefone, endereco);
		this.cpf = cpf;
		this.rg = rg;
		this.usuarioSistema = usuarioSistema;
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

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	
	
}
