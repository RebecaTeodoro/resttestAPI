package com.resttest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		
		Contact contact = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(contact));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private Contact convertDtoToEntity(ContactDTO dto) {
		Contact c = new Contact();
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
