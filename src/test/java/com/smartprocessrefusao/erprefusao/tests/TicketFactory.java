package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.dto.TicketDTO;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.projections.TicketReportProjection;

public class TicketFactory {

	public static Ticket createTicket() {
		return new Ticket(34950, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("10000.00"));
	}
	
	public static Ticket createTicketProductDispatch() {
		return new Ticket(35280, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("10000.00"));
	}

	public static TicketDTO createNewTicketDTO() {
		return new TicketDTO(34951, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("500.00"));
	}
	
	public static TicketDTO createTicketDTO() {
		return new TicketDTO(34950, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("10000.00"));
	}

	public static TicketDTO createTicketNoExistis() {
		return new TicketDTO(999, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("10000.00"));
	}
	
	public static TicketReportProjection createTicketProjection() {
		return new TicketReportProjection() {

			@Override
			public Integer getNumTicket() {
				return 34950;
			}

			@Override
			public LocalDate getDateTicket() {
				return LocalDate.of(2024, 9, 8);
			}

			@Override
			public BigDecimal getNetWeight() {
				return new BigDecimal("1500.00");
			}

			@Override
			public Integer getNumTicketId() {
				return 34950;
			}

			@Override
			public Long getPartnerId() {
				return 4L;
			}

			@Override
			public String getNamePartner() {
				return "Ecoalumi Alumínio S/A";
			}

			@Override
			public Long getScrapId() {
				return 1L;
			}

			@Override
			public String getScrapDescription() {
				return "Perfil de alumínio";
			}

			@Override
			public BigDecimal getAmountScrap() {
				return new BigDecimal("1000.00");
			}

			@Override
			public String getNumberPlate() {
				return "ABX-1200";
			}

		};
	}
}