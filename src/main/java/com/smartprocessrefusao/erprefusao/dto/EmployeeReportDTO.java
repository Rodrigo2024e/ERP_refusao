package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

public class EmployeeReportDTO {

	private Long id;
	private String name;
	private String cpf;
	private String email;
	private String cellPhone;
	private String telephone;
	private Long departamentId;
	private String departament;
	private String process;
	private Long id_address;
	private String street;
	private Integer numberAddress;
	private String complement;
	private String neighborhood;
	private String zipCode;
	private Long cityId;
	private String city;
	private String state;
	private String nameState;
	private String country;

	public EmployeeReportDTO() {

	}

	public EmployeeReportDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		cpf = entity.getCpf();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		departamentId = entity.getDepartament().getId();
		departament = entity.getDepartament().getName();
		process = entity.getDepartament().getProcess();
		id_address = entity.getAddress().getId();
		street = entity.getAddress().getStreet();
		numberAddress = entity.getAddress().getNumber();
		complement = entity.getAddress().getComplement();
		neighborhood = entity.getAddress().getNeighborhood();
		zipCode = entity.getAddress().getZipCode();
		cityId = entity.getAddress().getCity().getId();
		city = entity.getAddress().getCity().getName();
		state = entity.getAddress().getCity().getState().getUf();
		nameState = entity.getAddress().getCity().getState().getNameState();
		country = entity.getAddress().getCity().getState().getCountry();

	}

	public EmployeeReportDTO(EmployeeReportProjection projection) {
		this.id = projection.getId();
		this.name = projection.getName();
		this.cpf = projection.getCpf();
		this.email = projection.getEmail();
		this.cellPhone = projection.getCellPhone();
		this.telephone = projection.getTelephone();
		this.departamentId = projection.getDepartamentId();
		this.departament = projection.getDepartament();
		this.process = projection.getProcess();
		this.id_address = projection.getIdAddress();
		this.street = projection.getStreet();
		this.numberAddress = projection.getNumberAddress();
		this.complement = projection.getComplement();
		this.neighborhood = projection.getNeighborhood();
		this.zipCode = projection.getZipCode();
		this.cityId = projection.getCityId();
		this.city = projection.getCity();
		this.nameState = projection.getNameState();

		String uf = projection.getState();
		StateBrazil stateEnum = null;

		if (uf != null && !uf.isEmpty()) {
			stateEnum = StateBrazil.fromUf(uf);
		}

		this.state = uf;
		this.nameState = (stateEnum != null) ? stateEnum.getNameState() : "Estado não encontrado";
		this.country = (stateEnum != null) ? stateEnum.getCountry()
				: (projection.getCountry() != null ? projection.getCountry() : "País desconhecido");

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public Long getDepartamentId() {
		return departamentId;
	}

	public String getDepartament() {
		return departament;
	}

	public String getProcess() {
		return process;
	}

	public Long getId_address() {
		return id_address;
	}

	public String getStreet() {
		return street;
	}

	public Integer getNumberAddress() {
		return numberAddress;
	}

	public String getComplement() {
		return complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public Long getCityId() {
		return cityId;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getNameState() {
		return nameState;
	}

	public String getCountry() {
		return country;
	}

}
