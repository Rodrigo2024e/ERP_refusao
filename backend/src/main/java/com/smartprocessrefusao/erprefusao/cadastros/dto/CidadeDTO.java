
package com.smartprocessrefusao.erprefusao.cadastros.dto;

import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Cidade;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CidadeDTO {

	private Long id;
	
	@Size(min = 5, max = 100, message = "O nome deve ter entre 5 a 100 caracteres")
	@NotBlank(message = "O campo nome da cidade é obrigatório")
	private String cidade;
	
	@NotBlank(message = "O campo CEP é obrigatório")
	@Pattern(regexp = "\\d{2}.\\d{3}-\\d{3}", message = "O CEP deve estar no formato 00.000-000.")
	private String cep;
	
	@NotNull(message = "O campo UF do Estado é obrigatório")
	private String ufEstado;
	
	private String nomeEstado;

	private List<EnderecoDTO> enderecos = new ArrayList<>();
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Long id, String cidade, String cep, String ufEstado, String nomeEstado) {
		this.id = id;
		this.cidade = cidade;
		this.cep = cep;
		this.ufEstado = ufEstado;
		this.nomeEstado = nomeEstado;
		
	}
	
	public CidadeDTO(Cidade entity) {
		id = entity.getId();
		cidade = entity.getCidade();
		cep = entity.getCep();
		
		if(entity.getEstado().getUf() != null) {
			ufEstado = entity.getEstado().getUf();
			nomeEstado = entity.getEstado().getNome();
		}
		
		if (entity.getEnderecos() != null)
			for (Endereco end : entity.getEnderecos()) {
				enderecos.add(new EnderecoDTO(end));
			}
		
	}

	public Long getId() {
		return id;
	}

	public String getCidade() {
		return cidade;
	}

	public String getCep() {
		return cep;
	}

	public String getUfEstado() {
		return ufEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}

}
