package com.resttest.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resttest.entity.Contact;
import com.resttest.repository.ContactRepository;
import com.resttest.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	ContactRepository repository;
	
	@Override
	public Contact save(Contact c) {
		// TODO Auto-generated method stub
		return repository.save(c);
	}

	@Override
	public Optional<Contact> findByName(String name) {
		// TODO Auto-generated method stub
		return repository.findByNameEquals(name);
	}
	
	

}