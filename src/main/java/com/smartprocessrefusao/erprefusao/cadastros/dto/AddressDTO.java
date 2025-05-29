package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Address;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.AddressProjection;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDTO {

    private Long idAddress;

    @Size(min = 3, max = 30, message = "O nome deve ter entre 5 a 30 caracteres")
    private String street;

    @NotNull(message = "O numero é obrigatório")
    private Integer numberAddress;

    private String complement;

    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 a 50 caracteres")
    private String neighborhood;

    @Pattern(regexp = "\\d{2}.\\d{3}-\\d{3}", message = "O CEP deve estar no formato 00.000-000.")
    private String zipCode;

    private Long cityId;
    private String nameCity;
    private String ufState; 
    private String nameState; 
    private String country;   
    private Long people_id;

    public AddressDTO() {
    }

    public AddressDTO(Long idAddress, String street, Integer numberAddress, String complement, 
    		String neighborhood, String zipCode, Long cityId, String nameCity, String ufState, 
    		String nameState, String country, Long people_id) {
        this.idAddress = idAddress;
        this.street = street;
        this.numberAddress = numberAddress;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.cityId = cityId;
        this.nameCity = nameCity;
        this.ufState = ufState;
        this.nameState = nameState;
        this.country = country;
        this.people_id = people_id;
    }

    public AddressDTO(Address entity) {
    	idAddress = entity.getIdAddress();
        street = entity.getStreet();
        numberAddress = entity.getNumberAddress();
        complement = entity.getComplement();
        neighborhood = entity.getNeighborhood();
        zipCode = entity.getZipCode();
        cityId = entity.getCity().getId();
        nameCity = entity.getCity().getNameCity();
        ufState = entity.getCity().getUfState().getUf();
        nameState = entity.getCity().getUfState().getUf();
        country =  entity.getCity().getUfState().getCountry();
        people_id = entity.getPeople().getId();
    }

    public AddressDTO(AddressProjection projection) {
        this.idAddress = projection.getIdAddress();
        this.street = projection.getStreet();
        this.numberAddress = projection.getNumberAddress();
        this.complement = projection.getComplement();
        this.neighborhood = projection.getNeighborhood();
        this.zipCode = projection.getZipCode();
        this.cityId = projection.getCityId();
        this.nameCity = projection.getNameCity();
        this.ufState = projection.getUfState(); 

        if (projection.getUfState() != null) {
            StateBrazil stateEnum = StateBrazil.fromUf(projection.getUfState()); 
            if (stateEnum != null) {
                this.nameState = stateEnum.getNameState();
                this.country = stateEnum.getCountry();
            } else {
                this.nameState = null; 
                this.country = null;
            }
        } else {
            this.nameState = null;
            this.country = null;
        }

        this.people_id = projection.getPeopleId();
    }

	public Long getIdAddress() {
		return idAddress;
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

	public Long getPeople_id() {
		return people_id;
	}

	

}