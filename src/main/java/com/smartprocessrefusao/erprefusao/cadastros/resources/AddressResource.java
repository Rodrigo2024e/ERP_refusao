package com.smartprocessrefusao.erprefusao.cadastros.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.cadastros.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.AddressService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping(value = "/addresses") 
public class AddressResource {

    @Autowired
    private AddressService addressService;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping
    public ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable) {
        Page<AddressDTO> page = addressService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/search")
    public ResponseEntity<Page<AddressDTO>> searchAddresses(
            @RequestParam(name = "nameCity", required = false) String nameCity,
            @RequestParam(name = "addressId", required = false) Long addressId,
            Pageable pageable) {
        Page<AddressDTO> page = addressService.searchAddresses(nameCity, addressId, pageable);
        return ResponseEntity.ok(page);
    }
   
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<AddressDTO> insert(@Valid @RequestBody AddressDTO dto) {
      
        AddressDTO newDto = addressService.insert(dto);
   
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getIdAddress()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }
 
    @PutMapping(value = "/{id}")
	public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
		dto = addressService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
    	addressService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
