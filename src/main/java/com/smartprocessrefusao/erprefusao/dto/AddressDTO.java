package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.AddressReportProjection;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDTO {

	private Long id;

	@Size(min = 3, max = 30, message = "O nome deve ter entre 3 a 30 caracteres")
	private String street;

	@NotNull(message = "O numero é obrigatório")
	private Integer number;

	private String complement;

	@Size(min = 3, max = 50, message = "O nome deve ter entre 3 a 30 caracteres")
	private String neighborhood;

	@Pattern(regexp = "\\d{2}.\\d{3}-\\d{3}", message = "O CEP deve estar no formato 00.000-000.")
	private String zipCode;

	@NotNull(message = "O campo cidade não pode ser nulo")
	private Long cityId;
	private String name;
	private String state;
	private String nameState;
	private String country;

	@NotNull(message = "O campo pessoa não pode ser nulo")
	private Long peopleId;

	public AddressDTO() {
	}

	public AddressDTO(Long id, String street, Integer number, String complement, String neighborhood, String zipCode,
			Long cityId, String name, String state, String nameState, String country, Long peopleId) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.zipCode = zipCode;
		this.cityId = cityId;
		this.name = name;
		this.state = state;
		this.nameState = nameState;
		this.country = country;
		this.peopleId = peopleId;
	}

	public AddressDTO(Address entity) {
		this.id = entity.getId();
		this.street = entity.getStreet();
		this.number = entity.getNumber();
		this.complement = entity.getComplement();
		this.neighborhood = entity.getNeighborhood();
		this.zipCode = entity.getZipCode();
		this.cityId = entity.getCity().getId();
		this.name = entity.getCity().getName();
		this.state = entity.getCity().getState().getUf().toString();
		this.nameState = entity.getCity().getState().getNameState().toString();
		this.country = entity.getCity().getState().getCountry().toString();
		this.peopleId = entity.getPeople().getId();

	}

	public AddressDTO(AddressReportProjection projection) {
		this.id = projection.getId();
		this.street = projection.getStreet();
		this.number = projection.getNumber();
		this.complement = projection.getComplement();
		this.neighborhood = projection.getNeighborhood();
		this.zipCode = projection.getZipCode();
		this.cityId = projection.getCityId();
		this.name = projection.getName();
		this.state = projection.getState();
		this.peopleId = projection.getPeopleId();

		if (projection.getState() != null) {
			StateBrazil stateEnum = StateBrazil.fromUf(projection.getState());
			if (stateEnum != null) {
				this.nameState = stateEnum.getNameState();
				this.country = stateEnum.getCountry();
			} else {
				throw new ResourceNotFoundException("UF não pode ser vazia");
			}

			if (projection.getPeopleId() != null) {
				this.peopleId = projection.getPeopleId();
			} else {
				throw new ResourceNotFoundException("UF não pode ser vazia");
			}

		}
	}

	public Long getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public Integer getNumber() {
		return number;
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

	public String getName() {
		return name;
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

	public Long getPeopleId() {
		return peopleId;
	}

}