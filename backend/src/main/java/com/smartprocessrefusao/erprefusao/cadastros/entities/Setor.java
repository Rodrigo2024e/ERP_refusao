package com.smartprocessrefusao.erprefusao.cadastros.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_setor")
public class Setor implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setorNome;
    private String processo;

    @OneToMany(mappedBy = "setor")
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Setor() {
    }

    public Setor(Long id, String setorNome, String processo) {
        this.id = id;
        this.setorNome = setorNome;
        this.processo = processo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
  
}
