package com.smartprocessrefusao.erprefusao.cadastros.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_estado")
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nomeEstado;
	private String uf;
	
	@OneToMany(mappedBy = "estados")
	private List<Cidade> cidades = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "pais_id")
	private Pais paises;
	
	public Estado() {
		
	}

	public Estado(long id, String nomeEstado, String uf) {
		this.id = id;
		this.nomeEstado = nomeEstado;
		this.uf = uf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nome) {
		this.nomeEstado = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public Pais getPaises() {
		return paises;
	}
	
}
