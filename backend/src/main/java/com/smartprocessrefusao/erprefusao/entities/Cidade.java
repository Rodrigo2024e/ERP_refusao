package com.smartprocessrefusao.erprefusao.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cidade")
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String nome;
	private String cep;
	
	@OneToMany(mappedBy = "cidades")
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estados;
	
	public Cidade() {
		
	}

	public Cidade(long id, String nome, String cep) {
		this.id = id;
		this.nome = nome;
		this.cep = cep;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
}
