package com.resttest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.resttest.entity.Contact;



public interface ContactService {

	Contact save(Contact c);
	
	List<Contact> findByNameEquals(String name);
	
	Optional<Contact> findById(Long id);
	
	List<Contact> findAll();
	
	Page<Contact> findAll(int page);
	
	void deleteById(Long id);
	
	void updateContactSetFavoriteForId(Long id, Boolean favorite);

}
