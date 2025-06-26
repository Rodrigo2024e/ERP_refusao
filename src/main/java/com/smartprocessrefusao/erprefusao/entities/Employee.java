package com.smartprocessrefusao.erprefusao.entities;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_employee")
public class Employee extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private String rg;
	private Boolean sysUser;
	
	@OneToOne(mappedBy = "employee")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "sector_id")
	private Sector sector;
	
	public Employee() {
		
	}

	public Employee(String cpf, String rg, Boolean sysUser, User user, Sector sector) {
		super();
		this.cpf = cpf;
		this.rg = rg;
		this.sysUser = sysUser;
		this.user = user;
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


	public void setSysUser(Boolean sysUser) {
		this.sysUser = sysUser;
	}


	public Sector getSector() {
		return sector;
	}


	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
