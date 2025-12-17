package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.smartprocessrefusao.erprefusao.entities.Material;

import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.MaterialMovementReportProjection;
/*
public class ReceiptFactory {

	private static final long EXISTING_ID = 1L;

	public static Receipt createReceipt() {
		// Objeto ScrapReceipt usa setters por ser uma Entidade JPA
		Receipt receipt = new Receipt();

		receipt.setReceipt(TypeTransactionReceipt.BUY);
		receipt.setCosts(TypeCosts.DIRECT_COSTS);

		return receipt;
	}

	public static MaterialMovementDTO createScrapReceiptDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), null);
	}

	public static MaterialMovementDTO createWithoutArgumentsScrapReceiptDTO() {
		MaterialMovement scrapReceipt = createScrapReceipt();
		return new MaterialMovementDTO(scrapReceipt);
	}

	public static MaterialMovementDTO createInvalidScrapReceiptDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", null, null, null);
	}

	public static MaterialMovementDTO createScrapReceiptWithNullMetalYield() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), null);
	}

	public static MaterialMovementDTO createScrapReceiptDTOWithNulAmountAndlUnitValue() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", null, null, null);
	}

	// válido → cobre if (linha verde/amarela)
	public static MaterialMovementDTO createScrapReceiptWithNullDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), // amountScrap
				new BigDecimal("5.00"), // unitValue
				null);
	}

	// inválido → cobre else (linha vermelha)
	public static MaterialMovementDTO createScrapReceiptDTOWithNullAmountAndMetalYield() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", null, // amountScrap
				null, // unitValue
				null // totalValue
		);
	}

	public static MaterialMovementDTO createScrapReceiptPartnerDoesNotExistsDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, NON_EXISTING_PARTNER_ID,
				"INVALID", TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"));
	}

	public static MaterialMovementDTO createScrapReceiptInputDoesNotExistsDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), 999L,
				"INVALID", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"));
	}

	public static MaterialMovementDTO createScrapReceiptInvalidCostsDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", TypeTransactionReceipt.BUY.toString(), "INVALID", EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"));
	}

	public static MaterialMovementDTO createScrapReceiptInvalidTranscationDTO() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", "INVALID", TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"));
	}

	public static MaterialMovementDTO createScrapReceiptDTOWithNegativeAmount() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(),
				EXISTING_INPUT_ID, "PERFIL DE PROCESSO", new BigDecimal("-100.00"), // Valor negativo
				new BigDecimal("5.00"), new BigDecimal("500.00"));
	}

	// Outros métodos de fábrica ajustados para criar DTOs com valores específicos
	public static MaterialMovementDTO createScrapReceiptDTOWithNegativeYield() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(),
				EXISTING_INPUT_ID, "PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"),
				new BigDecimal("500.00"));
	}

	public static MaterialMovementDTO createScrapReceiptDTOWithNegativeUnitValue() {
		return new MaterialMovementDTO(EXISTING_ID, LocalDate.now(), EXISTING_TICKET_ID, EXISTING_PARTNER_ID,
				"ECOALUMI ALUMÍNIO S/A", TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(),
				EXISTING_INPUT_ID, "PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("-5.00"),
				new BigDecimal("500.00"));
	}

	public static Ticket createTicket() {
		Ticket ticket = new Ticket();
		ticket.setTicket(EXISTING_TICKET_ID);
		ticket.setNetWeight(new BigDecimal("200.00"));
		return ticket;
	}

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(EXISTING_PARTNER_ID);
		partner.setName("ECOALUMI ALUMÍNIO S/A");
		return partner;
	}

	public static Material createMaterial() {
		Material material = new Material();
		material.setId(EXISTING_INPUT_ID);
		material.setDescription("PERFIL DE PROCESSO");
		return material;
	}

	public static class ReceiptProjectionImpl implements MaterialMovementProjection {
		// ... (implementação do Projection permanece a mesma)
		private Long id;
		LocalDate date = LocalDate.now();
		private Integer ticketId;
		private Long partnerId;
		private String partnerName;
		private String transactionDescription;
		private String costs;
		private Long inputId;
		private String inputDescription;
		private BigDecimal amountScrap;
		private BigDecimal unitValue;
		private BigDecimal totalValue;

		// Construtor
		public ReceiptProjectionImpl(Long id, LocalDate date, Integer ticketId, Long partnerId, String partnerName,
				String transactionDescription, String costs, Long inputId, String inputDescription,
				BigDecimal amountScrap, BigDecimal unitValue, BigDecimal totalValue) {
			this.id = id;
			this.date = date;
			this.ticketId = ticketId;
			this.partnerId = partnerId;
			this.partnerName = partnerName;
			this.transactionDescription = transactionDescription;
			this.costs = costs;
			this.inputId = inputId;
			this.inputDescription = inputDescription;
			this.amountScrap = amountScrap;
			this.unitValue = unitValue;
			this.totalValue = totalValue;

		}

		@Override
		public Long getId() {
			return this.id;
		}

		@Override
		public LocalDate getDate() {
			return this.date;
		}

		@Override
		public Integer getTicketId() {
			return this.ticketId;
		}

		@Override
		public Long getPartnerId() {
			return this.partnerId;
		}

		@Override
		public String getPartnerName() {
			return this.partnerName;
		}

		@Override
		public String getTransactionDescription() {
			return this.transactionDescription;
		}

		@Override
		public String getCosts() {
			return this.costs;
		}

		@Override
		public Long getMaterialId() {
			return this.inputId;
		}

		@Override
		public String getInputDescription() {
			return this.inputDescription;
		}

		@Override
		public BigDecimal getAmount() {
			return this.amountScrap;
		}

		@Override
		public BigDecimal getUnitValue() {
			return this.unitValue;
		}

		@Override
		public BigDecimal getTotalValue() {
			return this.totalValue;
		}

	}

}
*/