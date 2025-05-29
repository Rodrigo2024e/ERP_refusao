package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;

public class FichaFuncionarioDTO {
	
	private Long id;
	private String nomePessoa;
	private String email;
	private String celular;
	private String telefone;
	private String cpf;
	private String rg;
	private Boolean usuarioSistema;
	private Long setorId;
	private String setorNome;
	private String setorProcesso;
	private Long enderecoId;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private Long cidadeId;
	private String nomeCidade;
	private String estado;
	private String uf;
	private String pais;
	
	public FichaFuncionarioDTO() {
		
	}

	public FichaFuncionarioDTO(Long id, String nomePessoa, String email, String celular, String telefone,
			String cpf, String rg, Boolean usuarioSistema, Long setorId, String setorNome, String setorProcesso,
			Long enderecoId, String logradouro, Integer numero, String complemento, String bairro, Long cidadeId,
			String nomeCidade, String estado, String uf, String pais) {
		this.id = id;
		this.nomePessoa = nomePessoa;
		this.email = email;
		this.celular = celular;
		this.telefone = telefone;
		this.cpf = cpf;
		this.rg = rg;
		this.usuarioSistema = usuarioSistema;
		this.setorId = setorId;
		this.setorNome = setorNome;
		this.setorProcesso = setorProcesso;
		this.enderecoId = enderecoId;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidadeId = cidadeId;
		this.nomeCidade = nomeCidade;
		this.estado = estado;
		this.uf = uf;
		this.pais = pais;
	}


	public FichaFuncionarioDTO(Funcionario entity) {
		id = entity.getId();
		nomePessoa = entity.getNomePessoa();
		email = entity.getEmail();
		celular = entity.getCelular();
		telefone = entity.getTelefone();
		cpf = entity.getCpf();
		rg = entity.getRg();
		usuarioSistema = entity.getUsuarioSistema();
		
		if (entity.getSetor() != null) {
			setorId = entity.getSetor().getId();
			setorNome = entity.getSetor().getSetorNome();
			setorProcesso = entity.getSetor().getProcesso();
		}

				if (entity.getEndereco() != null) {
					enderecoId = entity.getEndereco().getId();
					logradouro = entity.getEndereco().getLogradouro();
					numero = entity.getEndereco().getNumero();
					complemento = entity.getEndereco().getComplemento();
					bairro = entity.getEndereco().getBairro();
		
				}

						if (entity.getEndereco().getCidades() != null) {
							cidadeId = entity.getEndereco().getCidades().getId();
							nomeCidade = entity.getEndereco().getCidades().getCidade();
							
						}
						
						
							if (entity.getEndereco().getCidades().getEstado() != null) {
								estado = entity.getEndereco().getCidades().getEstado().getNome();
								uf = entity.getEndereco().getCidades().getEstado().getUf();
								pais = entity.getEndereco().getCidades().getEstado().getPais();
								
							}
						
						
	}

	public Long getId() {
		return id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public String getEmail() {
		return email;
	}

	public String getCelular() {
		return celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public Boolean getUsuarioSistema() {
		return usuarioSistema;
	}

	public Long getSetorId() {
		return setorId;
	}

	public String getSetorNome() {
		return setorNome;
	}

	public String getSetorProcesso() {
		return setorProcesso;
	}

	public Long getEnderecoId() {
		return enderecoId;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public Long getCidadeId() {
		return cidadeId;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public String getEstado() {
		return estado;
	}

	public String getUf() {
		return uf;
	}

	public String getPais() {
		return pais;
	}

	
}

