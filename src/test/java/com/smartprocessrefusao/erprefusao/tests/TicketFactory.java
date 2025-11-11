package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;
/*
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
	
	public static ReportReceiptTicketProjection createTicketProjection() {
		return new ReportReceiptTicketProjection() {

			@Override
			public Integer getTicket() {
				return 34950;
			}

			@Override
			public LocalDate getDateTicket() {
				return LocalDate.of(2024, 9, 8);
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
			public String getNumberPlate() {
				return "ABX-1200";
			}
			
			@Override
			public BigDecimal getNetWeight() {
				return new BigDecimal("1500.00");
			}
	

			@Override
			public Long getMaterialMovementId() {
				return 1L;
			}
			
			@Override
			public LocalDate getDate() {
				return LocalDate.of(2025, 7, 8);
			}

			@Override
			public Long getReceiptId() {
				return 1L;
			}
		
			@Override
			public String getTypeReceipt() {
				return TypeTransactionReceipt.BUY.toString();
			}
	
			@Override
			public String getTypeCosts() {
				return TypeCosts.DIRECT_COSTS.toString();
			}

			@Override
			public Long getMaterialMovementItemId() {
				return 1L;
			}
		
			@Override
			public String getDocumentNumber() {
				return "123.758";
			}
			
			@Override
			public BigDecimal getQuantity() {
				return new BigDecimal(1000.00);
			}
			
			@Override
			public BigDecimal getPrice() {
				return new BigDecimal(12.00);
			}
			
			@Override
			public BigDecimal getTotalValue() {
				return new BigDecimal(12000.00);
			}
			
			@Override
			public String getObservation() {
				return "Material para seleção";
			}
			
			
		};
	}
}
*/
