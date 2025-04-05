package com.smartprocessrefusao.erprefusao.cadastros.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_parceiro")
@PrimaryKeyJoinColumn(name = "id")
public class Parceiro extends Pessoa {
	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String ie;
	private boolean fornecedor;
	private boolean cliente;
	private boolean ativo;
	
	public Parceiro() {
		
	}

	public Parceiro(long id, String nome, String email, String celular, String telefone, Endereco endereco, String cnpj,
			String ie, boolean fornecedor, boolean cliente, boolean ativo) {
		super(id, nome, email, celular, telefone, endereco);
		this.cnpj = cnpj;
		this.ie = ie;
		this.fornecedor = fornecedor;
		this.cliente = cliente;
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
	
	
}
