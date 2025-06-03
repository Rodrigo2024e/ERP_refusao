package com.smartprocessrefusao.erprefusao.entities;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_employee")
public class Employee extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private String rg;
	private Boolean sysUser;
	
	@ManyToOne
	@JoinColumn(name = "sector_id")
	private Sector sector;
	
	public Employee() {
		
	}

	public Employee(Long id, String people, String email, String cellPhone, String telephone, 
			Address address, String cpf, String rg, Boolean sysUser, Sector sector) {
		super(id, people, email, cellPhone, telephone, address);
		this.cpf = cpf;
		this.rg = rg;
		this.sysUser = sysUser;
		this.sector = sector;
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

	public Boolean getSysUser() {
		return sysUser;
	}

	public void setSysUser(Boolean systemUser) {
		this.sysUser = systemUser;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}
