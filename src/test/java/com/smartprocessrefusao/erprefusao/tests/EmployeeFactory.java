package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
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
	        Employee employee = new Employee(
	            1L,
	            "João",
	            "joao@example.com",
	            "11999999999",
	            "1133333333",
	            null,
	            "123.456.789-00",
	            "MG-12.345.678",
	            true,
	            null,
	            createSector()
	        );
	        return employee;
	    }

	    public static EmployeeDTO createEmployeeDTO() {
	        return new EmployeeDTO(createEmployee());
	    }
	}

	   public static Employee createEmployee() {
	        Employee employee = new Employee();
	        employee.setId(1L);
	        employee.setName("João");
	        return employee;
	    }	   

}
