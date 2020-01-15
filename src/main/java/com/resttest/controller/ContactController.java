package com.resttest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resttest.dto.ContactDTO;
import com.resttest.entity.Contact;
import com.resttest.response.Response;
import com.resttest.service.ContactService;

@RestController
@RequestMapping("contact")
public class ContactController {

	@Autowired
	private ContactService service;
	
	@PostMapping
	public ResponseEntity<Response<ContactDTO>> create(@Valid @RequestBody ContactDTO dto, BindingResult result) {
		
		Response<ContactDTO> response = new Response<ContactDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Contact contact = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(contact));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	
	@PutMapping
	public ResponseEntity<Response<ContactDTO>> update(@Valid @RequestBody ContactDTO dto, BindingResult result) {
		Response<ContactDTO> response = new Response<ContactDTO>();
		
		Optional<Contact> contact = service.findById(dto.getId());
		
		if (!contact.isPresent()) {
			result.addError(new ObjectError("Contact", "Contato não encontrado"));
		} 
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Contact saved = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(saved));
		
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(value = "/{contactId}")
	public ResponseEntity<Response<String>> delete(@PathVariable("contactId") Long contactId) {
		Response<String> response = new Response<String>();
		
		Optional<Contact> contact = service.findById(contactId);
		
		if (!contact.isPresent()) {
			response.getErrors().add("Contato não encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} 
		
		service.deleteById(contactId);
		
		response.setData("Contato de id: " + contactId + " apagado com sucesso");
		
		return ResponseEntity.ok().body(response);
	}
	
	private Contact convertDtoToEntity(ContactDTO dto) {
		Contact c = new Contact();
		c.setId(dto.getId());
		c.setName(dto.getName());
		c.setCommercialPhone(dto.getCommercialPhone());
		c.setHomePhone(dto.getHomePhone());
		c.setCellPhone(dto.getCellPhone());
		c.setCommercialEmail(dto.getCommercialEmail());
		c.setPersonalEmail(dto.getPersonalEmail());
		c.setDateOfBirth(dto.getDateOfBirth());
		c.setFavorite(dto.isFavorite());
		
		return c;
	}
	
	private ContactDTO convertEntityToDto(Contact c) {
		ContactDTO dto = new ContactDTO();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setCommercialPhone(c.getCommercialPhone());
		dto.setHomePhone(c.getHomePhone());
		dto.setCellPhone(c.getCellPhone());
		dto.setCommercialEmail(c.getCommercialEmail());
		dto.setPersonalEmail(c.getPersonalEmail());
		dto.setDateOfBirth(c.getDateOfBirth());
		dto.setFavorite(c.isFavorite());
		
		return dto;
	}
}
