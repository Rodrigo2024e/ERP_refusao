package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

public class EmployeeReportDTO {

	private Long idPessoa;
	private String name;
	private String cpf;
	private String rg;
	private String email;
	private String cellPhone;
	private String telephone;
	private Boolean sysUser;
	private Long sectorId;
	private String nameSector;
	private String process;
	private Long id_address;
	private String street;
	private Integer numberAddress;
	private String complement;
	private String neighborhood;
	private String zipCode;
	private Long cityId;
	private String nameCity;
	private String ufState;
	private String nameState;
	private String country;

	public EmployeeReportDTO() {

	}

	public EmployeeReportDTO(Employee entity) {
		idPessoa = entity.getId();
		name = entity.getName();
		cpf = entity.getCpf();
		rg = entity.getRg();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		sysUser = entity.getSysUser();
		sectorId = entity.getSector().getId();
		nameSector = entity.getSector().getNameSector();
		process = entity.getSector().getProcess();
		id_address = entity.getAddress().getIdAddress();
		street = entity.getAddress().getStreet();
		numberAddress = entity.getAddress().getNumberAddress();
		complement = entity.getAddress().getComplement();
		neighborhood = entity.getAddress().getNeighborhood();
		zipCode = entity.getAddress().getZipCode();
		cityId = entity.getAddress().getCity().getId();
		nameCity = entity.getAddress().getCity().getNameCity();
		ufState = entity.getAddress().getCity().getUfState().getUf();
		nameState = entity.getAddress().getCity().getUfState().getNameState();
		country = entity.getAddress().getCity().getUfState().getCountry();

	}

	public EmployeeReportDTO(EmployeeReportProjection projection) {
		this.idPessoa = projection.getIdPessoa();
		this.name = projection.getName();
		this.cpf = projection.getCpf();
		this.rg = projection.getRg();
		this.email = projection.getEmail();
		this.cellPhone = projection.getCellPhone();
		this.telephone = projection.getTelephone();
		this.sysUser = projection.isSysUser();
		this.sectorId = projection.getSectorId();
		this.nameSector = projection.getNameSector();
		this.process = projection.getProcess();
		this.id_address = projection.getIdAddress();
		this.street = projection.getStreet();
		this.numberAddress = projection.getNumberAddress();
		this.complement = projection.getComplement();
		this.neighborhood = projection.getNeighborhood();
		this.zipCode = projection.getZipCode();
		this.cityId = projection.getCityId();
		this.nameCity = projection.getNameCity();
		this.nameState = projection.getNameState();

		String uf = projection.getUfState();
		StateBrazil stateEnum = null;

		if (uf != null && !uf.isEmpty()) {
		    stateEnum = StateBrazil.fromUf(uf);
		}

		this.ufState = uf;
		this.nameState = (stateEnum != null) ? stateEnum.getNameState() : "Estado não encontrado";
		this.country = (stateEnum != null) ? stateEnum.getCountry() : (projection.getCountry() != null ? projection.getCountry() : "País desconhecido");

	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
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

	public Boolean getSysUser() {
		return sysUser;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public String getNameSector() {
		return nameSector;
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

	public String getNameCity() {
		return nameCity;
	}

	public String getUfState() {
		return ufState;
	}

	public String getNameState() {
		return nameState;
	}

	public String getCountry() {
		return country;
	}

}
