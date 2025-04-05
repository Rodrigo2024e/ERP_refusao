package com.smartprocessrefusao.erprefusao.cadastros.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="tb_setor")
public class Setor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String setor;
	private String processo;
	
	@OneToMany(mappedBy = "setor")
	private List<Funcionario> funcionario = new ArrayList<>();
	
	public Setor() {
		
	}

	public Setor(long id, String setor, String processo) {
		this.id = id;
		this.setor = setor;
		this.processo = processo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getsetor() {
		return setor;
	}

	public void setsetor(String setor) {
		this.setor = setor;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}


	
}
