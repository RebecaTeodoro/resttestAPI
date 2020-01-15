package com.resttest.service;

import java.util.List;
import java.util.Optional;

import com.resttest.entity.Contact;

public interface ContactService {

	Contact save(Contact c);
	
	Optional<Contact> findByName(String name);
	
	Optional<Contact> findById(Long id);
	
	List<Contact> findAll();
	
	void deleteById(Long id);
}
