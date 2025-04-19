package com.smartprocessrefusao.erprefusao.cadastros.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_parceiro")
public class Parceiro extends Pessoa {
	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String ie;
	private Boolean fornecedor;
	private Boolean cliente;
	private Boolean ativo;
	
	public Parceiro() {
		
	}

	public Parceiro(Long id, String nome, String email, String celular, String telefone, Endereco endereco, String cnpj,
			String ie, Boolean fornecedor, Boolean cliente, Boolean ativo) {
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

	
	
}
