package com.smartprocessrefusao.erprefusao.cadastros.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pais")
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true)
	private String nomePais;
	
	@OneToMany(mappedBy = "paises")
	private List<Estado> estados = new ArrayList<>();
	
	
	
	public Pais() {
		
	}

	public Pais(long id, String nomePais) {
		this.id = id;
		this.nomePais = nomePais;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public List<Estado> getEstados() {
		return estados;
	}
	
	
	
}
