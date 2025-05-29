package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Partner;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.ReportPartnerProjection;

public class ReportPartnerDTO {

	private Long id;
	private String name;
	private String cnpj;
	private String ie;
	private String email;
	private String cellPhone;
	private String telephone;
	private Boolean supplier;
	private Boolean client;
	private Boolean active;
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
    
    public ReportPartnerDTO() {
    	
    }

	public ReportPartnerDTO(Partner entity) {
		id = entity.getId();
		name = entity.getName();
		cnpj = entity.getCnpj();
		ie = entity.getIe();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		supplier = entity.getSupplier();
		client = entity.getClient();
		active = entity.getActive();
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

	public ReportPartnerDTO(ReportPartnerProjection projection) {
		this.id = projection.getId();
		this.name = projection.getName();
		this.cnpj = projection.getCnpj();
		this.ie = projection.getIe();
		this.email = projection.getEmail();
		this.cellPhone = projection.getCellPhone();
		this.telephone = projection.getTelephone();
		this.supplier = projection.getSupplier();
		this.client = projection.getClient();
		this.active = projection.getActive();
		this.id_address = projection.getIdAddress();
		this.street = projection.getStreet();
		this.numberAddress = projection.getNumberAddress();
		this.complement = projection.getComplement();
		this.neighborhood = projection.getNeighborhood();
		this.zipCode = projection.getZipCode();
		this.cityId = projection.getCityId();
		this.nameCity = projection.getNameCity();
		this.ufState = projection.getUfState();
		this.nameState = projection.getNameState();
		this.country = projection.getCountry();
	    		
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
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getIe() {
		return ie;
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

	public Boolean getSupplier() {
		return supplier;
	}

	public Boolean getClient() {
		return client;
	}

	public Boolean getActive() {
		return active;
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
