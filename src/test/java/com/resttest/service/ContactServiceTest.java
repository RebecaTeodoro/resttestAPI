package com.resttest.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.resttest.entity.Contact;
import com.resttest.repository.ContactRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactServiceTest {

	@MockBean
	ContactRepository repository;
	
	@Autowired
	ContactService service;
	
	@Before
	public void setUp() {
		BDDMockito.given(repository.findByNameEquals(Mockito.anyString())).willReturn(Optional.of(new Contact()));
	}
	
	@Test
	public void testFindByName() {
		Optional<Contact> contact = service.findByName("nomeTeste");
		
		assertTrue(contact.isPresent());
	}
}
