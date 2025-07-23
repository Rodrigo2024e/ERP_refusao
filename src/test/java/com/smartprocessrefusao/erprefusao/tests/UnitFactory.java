package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.entities.Unit;

public class UnitFactory {

	public static Unit createUnit() {
		Unit unit = new Unit();
		unit.setId(1L);
		unit.setDescription("kilograma");
		unit.setAcronym("kg");
		return unit;
	}
		
	//para id conhecido
	public static Unit createUnit(Long id, String description, String acronym) {
		return new Unit(id, description, acronym);
	}
}
