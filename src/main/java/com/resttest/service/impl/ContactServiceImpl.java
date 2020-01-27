package com.resttest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resttest.entity.Contact;
import com.resttest.repository.ContactRepository;
import com.resttest.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	ContactRepository repository;
	
	@Value("${pagination.items_per_page}")
	private int itemsPerPage;
	
	@Override
	public Contact save(Contact c) {
		

		if (c.getCellPhone() == null && c.getCommercialPhone() == null && c.getHomePhone() == null) {
			throw new NullPointerException("Necessário preencher um telefone");	
		}

		if (c.getCommercialEmail() == null && c.getPersonalEmail() == null) {
			throw new NullPointerException("Necessário preencher um Email");	
	    }
		
		return repository.save(c);
	}

	@Override
	public List<Contact> findByNameEquals(String name) {
		return repository.findByNameEquals(name);
	}
	
	@Override
	public Optional<Contact> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public List<Contact> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Page<Contact> findAll(int page) {
		return repository.findAll(PageRequest.of(page,itemsPerPage, Sort.by("name").descending()));
	}
	
	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}


	@Override
	@Transactional
	public void updateContactSetFavoriteForId(Long id, Boolean favorite) {
		repository.updateContactSetFavoriteForId(id, favorite);
	}
	

}
