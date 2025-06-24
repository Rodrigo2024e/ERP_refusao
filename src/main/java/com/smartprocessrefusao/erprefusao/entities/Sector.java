package com.smartprocessrefusao.erprefusao.entities;

import java.util.HashSet;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_sector")
public class Sector implements IdProjection<Long> {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameSector;
    private String process;

    @OneToMany(mappedBy = "sector")
    private Set<Employee> employees = new HashSet<>();

    public Sector() {
    }

	public Sector(Long id, String nameSector, String process) {
		this.id = id;
		this.nameSector = nameSector;
		this.process = process;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameSector() {
		return nameSector;
	}

	public void setNameSector(String name) {
		this.nameSector = name;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	} 
    
}
