package com.smartprocessrefusao.erprefusao.cadastros.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FuncionarioDTO {
	
	private Long id;
	
	@Size(min = 5, max = 50, message = "O campo nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "O campo nome é obrigatório")
	private String nomePessoa;
	
	@NotEmpty(message = "Informe um e-mail válido")
    @Email
	private String email;
	
	@NotBlank(message = "O celular é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{5}-\\d{4}", message = "O celular deve estar no formato 00-00000-0000")
	private String celular;
	
	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{4}-\\d{4}", message = "O telefone fixo deve estar no formato 00-0000-0000")
	private String telefone;
	
	@NotBlank(message = "O campo CPF é obrigatório")
	@CPF
	private String cpf;
	
	@NotBlank(message = "O campo RG é obrigatório")
	private String rg;
	
	@NotNull(message = "Informe se o funcionário é usuário do sistema")
	private Boolean usuarioSistema;
	
	@NotNull(message = "Informe o setor do funcionário")
	private Long setorId;
	
	private String setorNome;
	private String setorProcesso;
	
	public FuncionarioDTO() {
		
	}

	public FuncionarioDTO(Long id,  String nomePessoa, String email, String celular, String telefone, 
			String cpf,  String rg, Boolean usuarioSistema, Long setorId, String setorNome, String setorProcesso) {
		
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
	
	}


	public FuncionarioDTO(Funcionario entity) {
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

	}

	public Long getIdPessoa() {
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

	
}

