package com.smartprocessrefusao.erprefusao.movimentacao.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_operacao")
public class Operacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAtual;
	private Boolean entrada;
	private String placa;
	private String parceiro;
	private String material;
	private Double preco;
	private Double valorTotal;
	private String tipoOperacao;
	private Integer tipoGasto;
	
	@ManyToOne
	@JoinColumn(name = "num_ticket", referencedColumnName = "numTicket")
	private Ticket tickets;
	
	public Operacao() {
		
	}

	public Operacao(LocalDate dataAtual, Boolean entrada, String placa, String parceiro, String material, Double preco,
			Double valorTotal, String tipoOperacao, Integer tipoGasto) {
		this.dataAtual = dataAtual;
		this.entrada = entrada;
		this.placa = placa;
		this.parceiro = parceiro;
		this.material = material;
		this.preco = preco;
		this.valorTotal = valorTotal;
		this.tipoOperacao = tipoOperacao;
		this.tipoGasto = tipoGasto;
	}

	public LocalDate getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(LocalDate dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getParceiro() {
		return parceiro;
	}

	public void setParceiro(String parceiro) {
		this.parceiro = parceiro;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Integer getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(Integer tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	
}
