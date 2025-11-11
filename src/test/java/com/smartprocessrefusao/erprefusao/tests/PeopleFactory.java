package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.People;

public class PeopleFactory {

	   public static People createPeople() {
	        People people = new Employee();
	        people.setId(1L);
	        people.setName("João");
	        return people;
	    }

	   public static EmployeeDepartamentDTO createPeopleDTOWithInvalidSectorId() {
		    People people = new Employee();
		    people.setId(1L);
		    people.setName("João da Silva");
		    people.setEmail("joao@email.com");

		    return new EmployeeDepartamentDTO();
		} 


}
