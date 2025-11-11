package com.smartprocessrefusao.erprefusao.tests;

/*
public class DispatchFactory {

	public static Ticket createTicket() {
		Ticket ticket = new Ticket();
		ticket.setTicket(35280);
		ticket.setNetWeight(new BigDecimal("10246"));
		return ticket;
	}
	
	public static TicketDTO createNewTicketDTO() {
		return new TicketDTO(41100, LocalDate.of(2024, 9, 8),  "ABX-1225", new BigDecimal("1200.00"));
	}

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(4L);
		partner.setName("ECOALUMI ALUMINIO S/A");
		return partner;
	}


	public static Dispatch createProductDispatch() {
		Dispatch entity = new Dispatch();
		entity.setId(1L);
		entity.setAmount(new BigDecimal("1024"));
		entity.setUnitValue(new BigDecimal("5.00"));
		entity.setTransaction(TypeTransactionOutGoing.SALE);
		entity.setTicket(createTicket());
		entity.setPartner(createPartner());

		return entity;
	}

	public static DispatchDTO createDispatchDTO() {
		return new DispatchDTO(1L, LocalDate.now(), 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}
	
	
	

	public static DispatchDTO createProductDispatchInvalidTransactionDTO() {
		return new DispatchDTO(1L, LocalDate.now(),35280, 4L, "ECOALUMI ALUMINIO S/A", "INVALID", 8L, "Tarugo de alumínio",
				new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static DispatchDTO createProductDispatchInvalidPartnerDTO() {
		return new DispatchDTO(1L, LocalDate.now(), 35280, 999L, "INVALID", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static DispatchDTO createProductDispatchInvalidProductDTO() {
		return new DispatchDTO(1L, LocalDate.now(), 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 999L,
				"INVALID",  new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static DispatchDTO createProductDispatchInvalidTicketDTO() {
		return new DispatchDTO(1L, LocalDate.now(), 9999, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio",  new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static DispatchDTO createProductDispatchAmountExceedsTicketWeightDTO() {
		return new DispatchDTO(1L, LocalDate.now(), 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio",  new BigDecimal("10246.00"), new BigDecimal("5.00"), null);
	}

	public static DispatchProjection createProjection() {
		return new DispatchProjection() {

			@Override
			public Long getId() {
				return 1L;
			}
			
			@Override
			public LocalDate getDate() {
				return LocalDate.now();
			}

			@Override
			public Integer getTicketId() {
				return 35280;
			}

			@Override
			public Long getPartnerId() {
				return 4L;
			}

			@Override
			public String getPartnerName() {
				return "ECOALUMI ALUMINIO S/A";
			}

			@Override
			public String getTransactionDescription() {
				return "BUY";
			}

			@Override
			public Long getProductId() {
				return 8L;
			}

			@Override
			public String getProductDescription() {
				return "Tarugo de alumínio";
			}

			@Override
			public String getAlloy() {
				return "AL6060";
			}

			@Override
			public Integer getBilletDiameter() {
				return 5;
			}

			@Override
			public Double getBilletLength() {
				return 6.00;
			}

			@Override
			public BigDecimal getAmount() {
				return new BigDecimal("1024");

			}

			@Override
			public BigDecimal getUnitValue() {
				return new BigDecimal("5.00");
			}

			@Override
			public BigDecimal getTotalValue() {
				return null;
			}
		};
	}
}
*/