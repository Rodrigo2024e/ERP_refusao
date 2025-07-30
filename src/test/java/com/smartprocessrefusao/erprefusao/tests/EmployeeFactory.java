package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.EmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;

public class EmployeeFactory {

	public class Factory {

		public static Sector createSector() {
			Sector sector = new Sector();
			sector.setId(1L);
			sector.setNameSector("Produção");
			return sector;
		}

		public static Employee createEmployee() {
			Employee employee = new Employee(1L, "Jonathas Junio", "jonathas@gmail.com", "44-12345-7652",
					"00-0000-0000", null, "123.456.789-00", "MG-12.345.678", true, null, createSector());
			return employee;
		}

		public static EmployeeSectorDTO createEmployeeDTO() {
			return new EmployeeSectorDTO(null, "Jonathas Junio", "jonathas@alunova.com", "44-12345-7652",
					"01-1000-1000", "058.651.619-03", "20.533.347-45", true, 12L, "Administração", "Comercial");
		}
	}

	public static Employee createEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("João");
		return employee;
	}

}
