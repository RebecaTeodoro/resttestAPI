package com.resttest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resttest.dto.ContactDTO;

import com.resttest.entity.Contact;
import com.resttest.response.Response;
import com.resttest.service.ContactService;

@RestController
@Api(value = "Contact")
@RequestMapping("contact")
public class ContactController {

	@Autowired
	private ContactService service;

	
	@PostMapping
	public ResponseEntity<Response<ContactDTO>> create(@Valid @RequestBody ContactDTO dto, BindingResult result) {
		
		Response<ContactDTO> response = new Response<ContactDTO>();
		
		Contact c = this.convertDtoToEntity(dto);
		
		List<String> errorValidateFields = c.validateFields(c);
		
		if (!errorValidateFields.isEmpty()) {
			for(String error : errorValidateFields){
				response.getErrors().add(error);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
		}
		
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Contact contact = service.save(c);
		
		response.setData(this.convertEntityToDto(contact));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@RequestMapping(value = "/contact", method =  RequestMethod.PUT, produces="application/json", consumes="application/json")
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
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		
		Optional<Contact> contact = service.findById(id);
		
		if (!contact.isPresent()) {
			response.getErrors().add("Contato não encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} 
		
		service.deleteById(id);
		
		response.setData("Contato de id: " + id + " apagado com sucesso");
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/page/{page}")
	public ResponseEntity<Response<Page<ContactDTO>>> findAllPage(@RequestParam(name = "page", defaultValue = "0") int page) {
		
		Response<Page<ContactDTO>> response = new Response<Page<ContactDTO>>();
		
		Page<Contact> list = service.findAll(page);
		
		Page<ContactDTO> dto = list.map(i -> this.convertEntityToDto(i));

		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping
	public ResponseEntity<Response<List<ContactDTO>>> findAll() {
		
		Response<List<ContactDTO>> response = new Response<List<ContactDTO>>();
		
		List<Contact> list = service.findAll();
		
		List<ContactDTO> dto = list.stream().map(i -> this.convertEntityToDto(i)).collect(Collectors.toList());  

		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}
	
	
	@PutMapping(value = "/updateFavorite/")
	public ResponseEntity<Response<String>> updateFavorite(@Valid @RequestBody ContactDTO dto, BindingResult result) {
		
		
		Response<String> response = new Response<String>();
		
		Optional<Contact> contact = service.findById(dto.getId());
		
		if (!contact.isPresent()) {
			result.addError(new ObjectError("Contact", "Contato não encontrado"));
		} 
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		service.updateContactSetFavoriteForId(dto.getId(), this.convertDtoToEntity(dto).getFavorite());
		
		
		response.setData("Contato de id: " + dto.getId() + " atualizado com sucesso");
		
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ContactDTO>> findById(@PathVariable("id") Long id) {
		
		Response<ContactDTO> response = new Response<ContactDTO>();
		
		Optional<Contact> contact = service.findById(id);
		
		if (!contact.isPresent()) {
			response.getErrors().add("Contato não encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} 
	
		response.setData(convertEntityToDto(contact.get()));
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Response<List<ContactDTO>>> findByName(@PathVariable("name") String name) {
		
		Response<List<ContactDTO>> response = new Response<List<ContactDTO>>();
		
		List<Contact> list = service.findByNameEquals(name);
		
		List<ContactDTO> dto = list.stream().map(i -> this.convertEntityToDto(i)).collect(Collectors.toList());  

		response.setData(dto);
		
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
		c.setFavorite(dto.getFavorite());
		
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
		dto.setFavorite(c.getFavorite());
		
		return dto;
	}
	
	

}
