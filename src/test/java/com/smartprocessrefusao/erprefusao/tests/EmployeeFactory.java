package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;

public class EmployeeFactory {

	
	public static Sector createSector() {
		Sector sector = new Sector();
		sector.setId(1L);
		sector.setNameSector("Produção");
		sector.setProcess("Corte de tarugos");
		return sector;
	}
	
	public static Employee createEmployee() {
		 Employee employee = new Employee();
		 employee.setId(1L);
		 employee.setname("João Carlos");
		 employee.setEmail("joao@gmail.com");
		 employee.setCellPhone("44-12244-1222");
		 employee.setTelephone("44-1442-2222");
		 employee.setCpf("198.149.318-29");
		 employee.setRg("20.121.347-45");
		 employee.setSysUser(true);
		 employee.setSector(createSector()); // ✅ Adiciona o setor aqui
		 return employee;
		
	}
	
	public static EmployeeDTO createEmployeeDTO() {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName("João Carlos");
		employeeDTO.setEmail("joao@gmail.com");
		employeeDTO.setCellPhone("44-12244-1222");
		employeeDTO.setTelephone("44-1442-2222");
		employeeDTO.setCpf("198.149.318-29");
		employeeDTO.setRg("20.121.347-45");
		employeeDTO.setSysUser(true);
		employeeDTO.setSectorId(1L);;
		return employeeDTO;
		
	}
}
