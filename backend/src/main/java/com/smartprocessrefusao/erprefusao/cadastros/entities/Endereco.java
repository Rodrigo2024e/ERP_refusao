package com.smartprocessrefusao.erprefusao.cadastros.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	
	
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidades;
	
	@OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
	private Pessoa pessoa;
	
	
	public Endereco() {
		
	}

	public Endereco(long id, String logradouro, Integer numero, String complemento, String bairro) {
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Cidade getCidade() {
		return cidades;
	}

	public void setCidade(Cidade cidade) {
		this.cidades = cidade;
	}

	
	
}
