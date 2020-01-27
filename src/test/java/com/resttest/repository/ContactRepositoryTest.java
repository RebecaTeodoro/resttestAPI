package com.resttest.repository;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.resttest.entity.Contact;
import com.resttest.repository.ContactRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactRepositoryTest {

	private static final String NAME = "NomeTeste";
	private static final String COMMERCIALPHONE = "1212-1212";
	private static final String HOMEPHONE = "1212-1212";
	private static final String CELLPHONE = "1212-1212";
	private static final String COMMERCIALEMAIL = "teste@teste.com.br";
	private static final String PERSONALEMAIL = "teste@teste.com.br";
	private static final Date DATEOFBIRTH = new Date();
	private static final Boolean FAVORITE = true;
	public Long idSave;
	@Autowired
	ContactRepository repository;
	
	@Before
	public void setUp() {
		Contact c = new Contact();

		c.setName(NAME);
		c.setCommercialPhone(COMMERCIALPHONE);
		c.setHomePhone(HOMEPHONE);
		c.setCellPhone(CELLPHONE);
		c.setCommercialEmail(COMMERCIALEMAIL);
		c.setPersonalEmail(PERSONALEMAIL);
		c.setDateOfBirth(DATEOFBIRTH);
		c.setFavorite(FAVORITE);
		
		Contact contact = repository.save(c);
		idSave = contact.getId();
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		Contact c = new Contact();

		c.setName(NAME);
		c.setCommercialPhone(COMMERCIALPHONE);
		c.setHomePhone(HOMEPHONE);
		c.setCellPhone(CELLPHONE);
		c.setCommercialEmail(COMMERCIALEMAIL);
		c.setPersonalEmail(PERSONALEMAIL);
		c.setDateOfBirth(DATEOFBIRTH);
		c.setFavorite(FAVORITE);
		
		Contact response = repository.save(c);
		
		assertNotNull(response);
		assertEquals(response.getName(), NAME);
	}
	
	
	@Test
	public void testUpdate() {
		Optional<Contact> contact = repository.findById(idSave);
		
		Contact c = contact.get();
		c.setName(NAME+" alterado");
	
		repository.save(c);
		
		Optional<Contact> newContact = repository.findById(idSave);
		
		assertEquals(newContact.get().getName(), "NomeTeste alterado");
		
	}
	
	@Test
	public void testUpdateFavorite() {
		Optional<Contact> contact = repository.findById(idSave);
		
		Contact c = contact.get();
	
		repository.updateContactSetFavoriteForId(c.getId(), false);
		
		Optional<Contact> newContact = repository.findById(idSave);
		
		assertEquals(newContact.get().getFavorite(), false);

	}
	
	@Test
	public void testDelete() {

		Contact c = new Contact(null, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE);
		
		repository.save(c);
		
		repository.deleteById(c.getId());
		
		Optional<Contact>  response = repository.findById(c.getId());
		
		assertFalse(response.isPresent());
	}
	
	@Test
	public void testFindById() {
		Optional<Contact> response = repository.findById(idSave);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getName(), NAME);
	}
	
	@Test
	public void testFindByName() {
		List<Contact> response = repository.findByNameEquals(NAME);
		
		assertNotNull(response);
		assertEquals(response.get(0).getName(), NAME);
	}
	
	@Test
	public void testFindAll() {

		Page<Contact> response = repository.findAll(PageRequest.of(0,10));
		assertEquals(response.getContent().size(), 1);
		assertEquals(response.getTotalElements(), 1);
		assertEquals(response.getContent().get(0).getId(), idSave);
		
	}
}
