package com.smartprocessrefusao.erprefusao.cadastros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartprocessrefusao.erprefusao.cadastros.dto.FuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping
	public ResponseEntity<Page<FuncionarioDTO>> findAll(Pageable pageable) {
		Page<FuncionarioDTO> list = funcionarioService.findAllPaged(pageable);		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id){
		FuncionarioDTO dto = funcionarioService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
}
