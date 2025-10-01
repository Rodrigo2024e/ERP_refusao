package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.dto.ScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ScrapReceiptProjection;

public class ScrapReceiptFactory {

	private static final long EXISTING_ID = 1L;
	private static final int EXISTING_TICKET_ID = 34950;
	private static final long EXISTING_PARTNER_ID = 3L;
	private static final long NON_EXISTING_PARTNER_ID = 999L;
	private static final long EXISTING_INPUT_ID = 1L;

	public static ScrapReceipt createScrapReceipt() {
		// Objeto ScrapReceipt usa setters por ser uma Entidade JPA
		ScrapReceipt scrapReceipt = new ScrapReceipt();
		scrapReceipt.setId(EXISTING_ID);
		scrapReceipt.setPartner(new Partner());
		scrapReceipt.setAmountScrap(new BigDecimal("100.00"));
		scrapReceipt.setUnitValue(new BigDecimal("5.00"));
		scrapReceipt.setTotalValue(null);
		scrapReceipt.setMetalYield(new BigDecimal("0.80"));
		scrapReceipt.setMetalWeight(null);
		scrapReceipt.setSlag(null);
		scrapReceipt.setCosts(TypeCosts.DIRECT_COSTS);
		scrapReceipt.setTransaction(TypeTransactionReceipt.BUY);
		scrapReceipt.setNumTicket(createTicket());
		scrapReceipt.setPartner(createPartner());
		scrapReceipt.setInput(createInput());
		return scrapReceipt;
	}
	
	public static ScrapReceiptDTO createScrapReceiptDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, 
				EXISTING_TICKET_ID, 
				4L, 
				"Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), 
				TypeCosts.DIRECT_COSTS.toString(), 
				EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", 
				new BigDecimal("100.00"), 
				new BigDecimal("5.00"), 
				null,
				new BigDecimal("0.80"),
				null, 
				null
				);
	}
	
	public static ScrapReceiptDTO createWithoutArgumentsScrapReceiptDTO() {
		ScrapReceipt scrapReceipt = createScrapReceipt();
		return new ScrapReceiptDTO(scrapReceipt);
	}
	
	public static ScrapReceiptDTO createInvalidScrapReceiptDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, 
				EXISTING_TICKET_ID, 
				4L, 
				"Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), 
				TypeCosts.DIRECT_COSTS.toString(), 
				EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", 
				null, 
				null, 
				null,
				null,
				null, 
				null
				);
	}
	public static ScrapReceiptDTO createScrapReceiptWithNullMetalYield() {
	    return new ScrapReceiptDTO(
	            EXISTING_ID,
	            EXISTING_TICKET_ID,
	            4L,
	            "Ecoalumi Aluminio S/A",
	            TypeTransactionReceipt.BUY.toString(),
	            TypeCosts.DIRECT_COSTS.toString(),
	            EXISTING_INPUT_ID,
	            "PERFIL DE PROCESSO",
	            new BigDecimal("100.00"),
	            new BigDecimal("5.00"),
	            null,
	            null, // ⚠️ metalYield nulo
	            null,
	            null
	    );
	}
	
	public static ScrapReceiptDTO createScrapReceiptDTOWithNulAmountAndlUnitValue() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, 4L, "Ecoalumi Aluminio S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", null, null, null, // <-- unitValue agora é nulo
				null, null, null);
	}

	// válido → cobre if (linha verde/amarela)
	public static ScrapReceiptDTO createScrapReceiptWithNullDTO() {
	    return new ScrapReceiptDTO(
	        EXISTING_ID, 
	        EXISTING_TICKET_ID, 
	        4L, 
	        "Ecoalumi Aluminio S/A",
	        TypeTransactionReceipt.BUY.toString(), 
	        TypeCosts.DIRECT_COSTS.toString(), 
	        EXISTING_INPUT_ID,
	        "PERFIL DE PROCESSO", 
	        new BigDecimal("100.00"), // amountScrap
	        new BigDecimal("5.00"),   // unitValue
	        null,                     // totalValue (vai ser calculado)
	        new BigDecimal("0.80"),   // metalYield
	        null,                     // metalWeight (vai ser calculado)
	        null
	    );
	}

	// inválido → cobre else (linha vermelha)
	public static ScrapReceiptDTO createScrapReceiptDTOWithNullAmountAndMetalYield() {
	    return new ScrapReceiptDTO(
	        EXISTING_ID, 
	        EXISTING_TICKET_ID, 
	        4L, 
	        "Ecoalumi Aluminio S/A",
	        TypeTransactionReceipt.BUY.toString(), 
	        TypeCosts.DIRECT_COSTS.toString(), 
	        EXISTING_INPUT_ID,
	        "PERFIL DE PROCESSO", 
	        null, // amountScrap
	        null, // unitValue
	        null, // totalValue
	        null, // metalYield
	        null, // metalWeight
	        null
	    );
	}


	public static ScrapReceiptDTO createScrapReceiptPartnerDoesNotExistsDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, NON_EXISTING_PARTNER_ID, "INVALID",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"),
				new BigDecimal("0.80"), new BigDecimal("80.00"), new BigDecimal("20.00"));
	}
	
	public static ScrapReceiptDTO createScrapReceiptInputDoesNotExistsDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), 999L,
				"INVALID", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"),
				new BigDecimal("0.80"), new BigDecimal("80.00"), new BigDecimal("20.00"));
	}

	public static ScrapReceiptDTO createScrapReceiptInvalidCostsDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.BUY.toString(), "INVALID", EXISTING_INPUT_ID, "PERFIL DE PROCESSO",
				new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"), new BigDecimal("0.80"),
				new BigDecimal("80.00"), new BigDecimal("20.00"));
	}

	public static ScrapReceiptDTO createScrapReceiptInvalidTranscationDTO() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A", "INVALID",
				TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID, "PERFIL DE PROCESSO", new BigDecimal("100.00"),
				new BigDecimal("5.00"), new BigDecimal("500.00"), new BigDecimal("0.80"), new BigDecimal("80.00"),
				new BigDecimal("20.00"));
	}

	public static ScrapReceiptDTO createScrapReceiptDTOWithNegativeAmount() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("-100.00"), // Valor negativo
				new BigDecimal("5.00"), new BigDecimal("500.00"), new BigDecimal("0.80"), new BigDecimal("80.00"),
				new BigDecimal("20.00"));
	}

	// Outros métodos de fábrica ajustados para criar DTOs com valores específicos
	public static ScrapReceiptDTO createScrapReceiptDTOWithNegativeYield() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"),
				new BigDecimal("-0.80"), // Valor negativo
				new BigDecimal("80.00"), new BigDecimal("20.00"));
	}

	public static ScrapReceiptDTO createScrapReceiptDTOWithNegativeUnitValue() {
		return new ScrapReceiptDTO(EXISTING_ID, EXISTING_TICKET_ID, EXISTING_PARTNER_ID, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.BUY.toString(), TypeCosts.DIRECT_COSTS.toString(), EXISTING_INPUT_ID,
				"PERFIL DE PROCESSO", new BigDecimal("100.00"), new BigDecimal("-5.00"), // Valor negativo
				new BigDecimal("500.00"), new BigDecimal("0.80"), new BigDecimal("80.00"), new BigDecimal("20.00"));
	}

	public static Ticket createTicket() {
		Ticket ticket = new Ticket();
		ticket.setNumTicket(EXISTING_TICKET_ID);
		ticket.setNetWeight(new BigDecimal("200.00"));
		return ticket;
	}

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(EXISTING_PARTNER_ID);
		partner.setName("ECOALUMI ALUMÍNIO S/A");
		return partner;
	}

	public static Input createInput() {
		Input input = new Input();
		input.setId(EXISTING_INPUT_ID);
		input.setDescription("PERFIL DE PROCESSO");
		return input;
	}

	public static class ScrapReceiptProjectionImpl implements ScrapReceiptProjection {
		// ... (implementação do Projection permanece a mesma)
		private Long id;
		private Integer numTicketId;
		private Long partnerId;
		private String partnerName;
		private String transactionDescription;
		private String costs;
		private Long inputId;
		private String inputDescription;
		private BigDecimal amountScrap;
		private BigDecimal unitValue;
		private BigDecimal totalValue;
		private BigDecimal metalYield;
		private BigDecimal metalWeight;
		private BigDecimal slag;

		// Construtor
		public ScrapReceiptProjectionImpl(Long id, Integer numTicketId, Long partnerId, String partnerName,
				String transactionDescription, String costs, Long inputId, String inputDescription,
				BigDecimal amountScrap, BigDecimal unitValue, BigDecimal totalValue, BigDecimal metalYield,
				BigDecimal metalWeight, BigDecimal slag) {
			this.id = id;
			this.numTicketId = numTicketId;
			this.partnerId = partnerId;
			this.partnerName = partnerName;
			this.transactionDescription = transactionDescription;
			this.costs = costs;
			this.inputId = inputId;
			this.inputDescription = inputDescription;
			this.amountScrap = amountScrap;
			this.unitValue = unitValue;
			this.totalValue = totalValue;
			this.metalYield = metalYield;
			this.metalWeight = metalWeight;
			this.slag = slag;
		}

		@Override
		public Long getId() {
			return this.id;
		}

		@Override
		public Integer getNumTicketId() {
			return this.numTicketId;
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
		public Long getInputId() {
			return this.inputId;
		}

		@Override
		public String getInputDescription() {
			return this.inputDescription;
		}

		@Override
		public BigDecimal getAmountScrap() {
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

		@Override
		public BigDecimal getMetalYield() {
			return this.metalYield;
		}

		@Override
		public BigDecimal getMetalWeight() {
			return this.metalWeight;
		}

		@Override
		public BigDecimal getSlag() {
			return this.slag;
		}
	}

	
}