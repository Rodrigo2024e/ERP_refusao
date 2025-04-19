package com.smartprocessrefusao.erprefusao.cadastros.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartprocessrefusao.erprefusao.cadastros.dto.FichaFuncionarioDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.FichaFuncionarioService;

@RestController
@RequestMapping(value = "/funcionariosRel")
public class FichaFuncionarioResource {

	@Autowired
	private FichaFuncionarioService fichaFuncionarioService;
	
	@GetMapping
	public ResponseEntity<Page<FichaFuncionarioDTO>> findAll(Pageable pageable) {
		Page<FichaFuncionarioDTO> list = fichaFuncionarioService.findAllPaged(pageable);		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FichaFuncionarioDTO> findById(@PathVariable Long id){
		FichaFuncionarioDTO dto = fichaFuncionarioService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
}
