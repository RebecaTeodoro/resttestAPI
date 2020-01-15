package com.resttest.repository;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.resttest.entity.Contact;
import com.resttest.repository.ContactRepository;

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

	private static final String NAME = "NomeTest";
	@Autowired
	ContactRepository repository;
	
	@Before
	public void setUp() {
		Contact c = new Contact();
		c.setName(NAME);
		c.setCommercialPhone("1212-1212");
		c.setHomePhone("1212-1212");
		c.setCellPhone("1212-1212");
		c.setCommercialEmail("teste@teste.com.br");
		c.setPersonalEmail("teste@teste.com.br");
		c.setDateOfBirth(new Date());
		c.setFavorite(true);
		
		repository.save(c);
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		Contact c = new Contact();
		c.setName("NomeTest");
		c.setCommercialPhone("1212-1212");
		c.setHomePhone("1212-1212");
		c.setCellPhone("1212-1212");
		c.setCommercialEmail("teste@teste.com.br");
		c.setPersonalEmail("teste@teste.com.br");
		c.setDateOfBirth(new Date());
		c.setFavorite(true);
		
		Contact response = repository.save(c);
		
		assertNotNull(response);
		
		
	}
	
	@Test
	public void testFindByName() {
		Optional<Contact> response = repository.findByNameEquals(NAME);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getName(), NAME);
	}
	
	@Test
	public void testFindAll() {
		List<Contact> response = repository.findAll();
		assertEquals(response.size(), 1);
		
	}
}
