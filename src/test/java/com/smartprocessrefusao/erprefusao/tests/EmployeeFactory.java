package com.smartprocessrefusao.erprefusao.tests;

import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.enumerados.EmployeePosition;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

public class EmployeeFactory {

	public class Factory {
		
		public static User createUser() {
			Employee emp = new Employee(1L, "John Doe", "LUCIANO@GMAIL.COM", "99999-9999", "9999-9999", null,
					"99.999.999-99", null, null, null);
			Role role = new Role(1L, "ROLE_USER");

			User user = new User(1L, "nomeUsuario", "123456", emp);
			user.addRole(role);
			return user;
		}

		public static Address createAddress() {
			Address address = new Address();
			address.setId(1L);
			address.setStreet("RUA A");
			address.setNumber(123);
			address.setComplement("CASA");
			address.setNeighborhood("CENTRO");
			address.setZipCode("12345-678");
			address.setCity(CityFactory.createCity());
			address.setPeople(PeopleFactory.createPeople());
			return address;
		}

		public static Departament createDepartament() {
			Departament departament = new Departament();
			departament.setId(1L);
			departament.setName("PRODUÇÃO");
			departament.setProcess("CORTE DE TARUGOS");
			return departament;
		}

		public static Employee createEmployee() {
			Employee employee = new Employee();
			employee.setId(1L);
			employee.setName("JONATHAS JUNIO");
			employee.setEmail("jonathas@alunova.com");
			employee.setCellPhone("44-12345-7652");
			employee.setTelephone("01-1000-1000");
			employee.setAddress(createAddress());
			employee.setCpf("058.651.619-03");
			employee.setDateOfBirth(LocalDate.now());
			employee.setUser(createUser());
			employee.setDepartament(createDepartament());
			return employee;
			
		}

		public static EmployeeDepartamentDTO createEmployeeDTO() {
			return new EmployeeDepartamentDTO(null, 
					"Luciano R Carvalho", 
					"luciano@gmail.com.br", 
					"44-14244-1222",
					"44-1442-2222", 
					"111.000.111-49", 
					LocalDate.now(),  
					1L, 
					"Produção", 
					"Recebimento e classificação de sucata",
					EmployeePosition.GERENTE_INDUSTRIAL);
		}
	}

	public static EmployeeReportProjection create() {
		return new EmployeeReportProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public String getName() {
				return "JONATHAS JUNIO";
			}

			@Override
			public String getCpf() {
				return "058.651.619-03";
			}

			@Override
			public String getRg() {
				return "20.533.347-45";
			}

			@Override
			public String getEmail() {
				return "jonathas@alunova.com";
			}

			@Override
			public String getCellPhone() {
				return "44-12345-7652";
			}

			@Override
			public String getTelephone() {
				return "01-1000-1000";
			}

			@Override
			public boolean isSysUser() {
				return true;
			}

			@Override
			public Long getDepartamentId() {
				return 12L;
			}

			@Override
			public String getDepartament() {
				return "Administração";
			}

			@Override
			public String getProcess() {
				return "Comercial";
			}

			@Override
			public Long getIdAddress() {
				return 8L;
			}

			@Override
			public String getStreet() {
				return "RUA CENTRAL";
			}

			@Override
			public Integer getNumberAddress() {
				return 253;
			}

			@Override
			public String getComplement() {
				return "CASA A";
			}

			@Override
			public String getNeighborhood() {
				return "CENTRO";
			}

			@Override
			public String getZipCode() {
				return "87.080.130";
			}

			@Override
			public Long getCityId() {
				return 2L;
			}

			@Override
			public String getCity() {
				return "MARINGÁ";
			}

			@Override
			public String getState() {
				return "PR";
			}

			@Override
			public String getNameState() {
				return "PARANÁ";
			}

			@Override
			public String getCountry() {
				return "BRASIL";
			}
		};

	}

}
