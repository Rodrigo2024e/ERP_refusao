package com.smartprocessrefusao.erprefusao.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_employee")
public class Employee extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String cpf;

	private LocalDate dateOfBirth;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "departament_id", nullable = false)
	private Departament departament;

	@ManyToMany
	@JoinTable(name = "tb_employee_melting", 
	joinColumns = @JoinColumn(name = "employee_id"), 
	inverseJoinColumns = @JoinColumn(name = "melting_id"))
	private Set<Melting> meltings = new HashSet<>();

	public Employee() {

	}

	public Employee(Long id, String name, String email, String cellPhone, String telephone, Address address, String cpf,
			LocalDate dateOfBirth,  User user, Departament departament) {
		super(id, name, email, cellPhone, telephone, address);
		this.cpf = cpf;
		this.dateOfBirth = dateOfBirth;
		this.user = user;
		this.departament = departament;

	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Departament getDepartament() {
		return departament;
	}

	public void setDepartament(Departament departament) {
		this.departament = departament;
	}

	public Set<Melting> getMeltings() {
		return meltings;
	}

	public void setMeltings(Set<Melting> meltings) {
		this.meltings = meltings;
	}

}
