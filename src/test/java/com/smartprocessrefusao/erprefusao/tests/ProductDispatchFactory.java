package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchReportProjection;

public class ProductDispatchFactory {

	public static Ticket createTicket() {
		Ticket ticket = new Ticket();
		ticket.setNumTicket(35000);
		ticket.setNetWeight(new BigDecimal("1000.00"));
		return ticket;
	}

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(1L);
		partner.setName("Parceiro Teste");
		return partner;
	}

	public static Product createProduct() {
		Product product = new Product();
		product.setId(1L);
		product.setDescription("Produto Teste");
		return product;
	}

	public static ProductDispatch createEntity(Ticket ticket, Partner partner, Product product) {
		ProductDispatch entity = new ProductDispatch();
		entity.setId(1L);
		entity.setAmountProduct(new BigDecimal("1000.00"));
		entity.setUnitValue(new BigDecimal("5.00"));
		entity.setTransaction(TypeTransactionOutGoing.SALE);
		entity.setNumTicket(ticket);
		entity.setPartner(partner);
		entity.setProduct(product);
		return entity;
	}

	public static ProductDispatchDTO createDTO() {
		return new ProductDispatchDTO(1L, 35000, 1L, "Cliente Teste", TypeTransactionOutGoing.SALE.toString(), 1L,
				"Produto Teste", 6060, 5, 6.00, new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createInvalidTransactionDTO() {
		return new ProductDispatchDTO(1L, 35000, 1L, "Cliente Teste", "INVALID", 1L, "Produto Teste", 6060, 5, 6.00,
				new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createInvalidPartnerDTO() {
		return new ProductDispatchDTO(1L, 35000, 999L, "INVALID", TypeTransactionOutGoing.SALE.toString(), 1L,
				"Produto Teste", 6060, 5, 6.00, new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createInvalidProductDTO() {
		return new ProductDispatchDTO(1L, 35000, 1L, "Cliente Teste", TypeTransactionOutGoing.SALE.toString(), 999L,
				"INVALID", 6060, 5, 6.00, new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createInvalidTicketDTO() {
		return new ProductDispatchDTO(1L, 9999, 1L, "Cliente Teste", TypeTransactionOutGoing.SALE.toString(), 1L,
				"Produto Teste", 6060, 5, 6.00, new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}

	public static ProductDispatchDTO createAmountExceedsTicketWeightDTO() {
		return new ProductDispatchDTO(1L, 35000, 1L, "Cliente Teste", TypeTransactionOutGoing.SALE.toString(), 1L,
				"Produto Teste", 6060, 5, 6.00, new BigDecimal("1000.00"), new BigDecimal("5.00"), null);
	}
	
	public static ProductDispatchDTO createAmountProductNullDTO() {
		return new ProductDispatchDTO(1L, 35000, 1L, "Cliente Teste", TypeTransactionOutGoing.SALE.toString(), 1L,
				"Produto Teste", 6060, 5, 6.00, null, null, null);
	}

	public static ProductDispatchReportProjection createProjection() {
		return new ProductDispatchReportProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public Integer getNumTicketId() {
				return 35000;
			}

			@Override
			public Long getPartnerId() {
				return 1L;
			}

			@Override
			public String getPartnerName() {
				return "Partner Teste";
			}

			@Override
			public String getTransactionDescription() {
				return "BUY";
			}

			@Override
			public Long getProductId() {
				return 1L;
			}

			@Override
			public String getProductDescription() {
				return "Product Teste";
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
				return new BigDecimal("1000.00");

			}

			@Override
			public BigDecimal getUnitValue() {
				return new BigDecimal("5.00");
			}

			@Override
			public BigDecimal getTotalValue() {
				return new BigDecimal("5000.00");
			}
		};
	}
}
