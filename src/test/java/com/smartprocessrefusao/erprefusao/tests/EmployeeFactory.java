package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.EmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

public class EmployeeFactory {

	public class Factory {

		public static Sector createSector() {
			Sector sector = new Sector();
			sector.setId(1L);
			sector.setNameSector("PRODUÇÃO");
			return sector;
		}

		public static Employee createEmployee() {
			Employee employee = new Employee(1L, "JONATHAS JUNIO", "jonathas@gmail.com", "44-12345-7652",
					"00-0000-0000", null, "123.456.789-00", "MG-12.345.678", true, null, createSector());
			return employee;
		}

		public static EmployeeSectorDTO createEmployeeDTO() {
			return new EmployeeSectorDTO(null, "JONATHAS JUNIO", "jonathas@alunova.com", "44-12345-7652",
					"01-1000-1000", "058.651.619-03", "20.533.347-45", true, 12L, "Administração", "Comercial");
		}
	}

	public static Employee createEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("João");
		return employee;
	}

	public static EmployeeReportProjection create() {
		return new EmployeeReportProjection() {

			@Override
			public Long getIdPessoa() {
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
			public Long getSectorId() {
				return 12L;
			}

			@Override
			public String getNameSector() {
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
			public String getNameCity() {
				return "MARINGÁ";
			}

			@Override
			public String getUfState() {
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
