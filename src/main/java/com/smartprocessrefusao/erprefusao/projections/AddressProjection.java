package com.smartprocessrefusao.erprefusao.projections;

public interface AddressProjection {
	    Long getIdAddress();
	    String getStreet();
	    Integer getNumberAddress();
	    String getComplement();
	    String getNeighborhood();
	    String getZipCode();
	    Long getCityId();
	    String getNameCity();
	    String getUfState();
	    Long getPeopleId();
}

