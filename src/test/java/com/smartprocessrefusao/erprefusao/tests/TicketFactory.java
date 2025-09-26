package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.dto.TicketDTO;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.projections.TicketReportProjection;

public class TicketFactory {

	public static Ticket createTicket() {
		return new Ticket(1001, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("500.00"));
	}

	public static TicketDTO createTicketDTO() {
		return new TicketDTO(1001, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("500.00"));
	}

	public static TicketDTO createTicketNoExistis() {
		return new TicketDTO(999, LocalDate.of(2024, 9, 8), "ABC-1234", new BigDecimal("500.00"));
	}
	
	public static TicketReportProjection createTicketProjection() {
		return new TicketReportProjection() {

			@Override
			public Integer getNumTicket() {
				return 1001;
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
				return 1001;
			}

			@Override
			public Long getPartnerId() {
				return 5L;
			}

			@Override
			public String getNamePartner() {
				return "Alunova";
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
				// TODO Auto-generated method stub
				return null;
			}

		};
	}
}