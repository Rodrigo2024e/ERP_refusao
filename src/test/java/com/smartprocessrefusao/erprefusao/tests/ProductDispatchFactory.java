package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.TicketDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchProjection;

public class ProductDispatchFactory {

	public static Ticket createTicket() {
		Ticket ticket = new Ticket();
		ticket.setNumTicket(35280);
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

	public static Product createProduct() {
		Product product = new Product();
		product.setId(8L);
		product.setDescription("Tarugo de alumínio");
		return product;
	}

	public static ProductDispatch createProductDispatch() {
		ProductDispatch entity = new ProductDispatch();
		entity.setId(6L);
		entity.setAmountProduct(new BigDecimal("1024"));
		entity.setUnitValue(new BigDecimal("5.00"));
		entity.setTransaction(TypeTransactionOutGoing.SALE);
		entity.setNumTicket(createTicket());
		entity.setPartner(createPartner());
		entity.setProduct(createProduct());
		return entity;
	}

	public static ProductDispatchDTO createProductDispatchDTO() {
		return new ProductDispatchDTO(1L, 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", 6060, 5, 6.00, new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createProductDispatchInvalidTransactionDTO() {
		return new ProductDispatchDTO(1L, 35280, 4L, "ECOALUMI ALUMINIO S/A", "INVALID", 8L, "Tarugo de alumínio", 6060, 5, 6.00,
				new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createProductDispatchInvalidPartnerDTO() {
		return new ProductDispatchDTO(1L, 35280, 999L, "INVALID", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", 6060, 5, 6.00, new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createProductDispatchInvalidProductDTO() {
		return new ProductDispatchDTO(1L, 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 999L,
				"INVALID", 6060, 5, 6.00, new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createProductDispatchInvalidTicketDTO() {
		return new ProductDispatchDTO(1L, 9999, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", 6060, 5, 6.00, new BigDecimal("1024"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createProductDispatchAmountExceedsTicketWeightDTO() {
		return new ProductDispatchDTO(1L, 35280, 4L, "ECOALUMI ALUMINIO S/A", TypeTransactionOutGoing.SALE.toString(), 8L,
				"Tarugo de alumínio", 6060, 5, 6.00, new BigDecimal("10246.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchProjection createProjection() {
		return new ProductDispatchProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public Integer getNumTicketId() {
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
			public Integer getAlloy() {
				return 6060;
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
			public BigDecimal getAmountProduct() {
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
