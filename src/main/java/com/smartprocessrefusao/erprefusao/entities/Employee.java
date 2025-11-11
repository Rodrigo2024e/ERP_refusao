package com.smartprocessrefusao.erprefusao.entities;

import java.util.Objects;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_employee")
public class Employee extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String cpf;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private User user;

	@ManyToOne
	@JoinColumn(name = "departament_id")
	private Departament departament;

	public Employee() {

	}

	public Employee(Long id, String name, String email, String cellPhone, String telephone, Address address, String cpf,
			User user, Departament departament) {
		super(id, name, email, cellPhone, telephone, address);
		this.cpf = cpf;
		this.user = user;
		this.departament = departament;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cpf);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(cpf, other.cpf);
	}

}
