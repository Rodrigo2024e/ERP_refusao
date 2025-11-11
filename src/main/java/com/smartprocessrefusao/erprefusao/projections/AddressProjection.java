package com.smartprocessrefusao.erprefusao.projections;

public interface AddressProjection {
	    Long getId();
	    String getStreet();
	    Integer getNumber();
	    String getComplement();
	    String getNeighborhood();
	    String getZipCode();
	    Long getCityId();
	    String getName();
	    String getState();
	    Long getPeopleId();
}

