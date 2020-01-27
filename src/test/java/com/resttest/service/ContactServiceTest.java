package com.resttest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
	
	private static final String NAME = "NomeTeste";
	private static final String COMMERCIALPHONE = "1212-1212";
	private static final String HOMEPHONE = "1212-1212";
	private static final String CELLPHONE = "1212-1212";
	private static final String COMMERCIALEMAIL = "teste@teste.com.br";
	private static final String PERSONALEMAIL = "teste@teste.com.br";
	private static final Date DATEOFBIRTH = new Date();
	private static final Boolean FAVORITE = true;
	
	
	@Test
	public void testSave() {
		BDDMockito.given(repository.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		Contact response = service.save(getMockContact());
		
		assertNotNull(response);
		assertEquals(response.getName(), NAME);
		assertEquals(response.getCellPhone(), CELLPHONE);
		
	}
	
	
	@Test
	public void testUpdate() {
		BDDMockito.given(repository.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		Contact response = service.save(getMockContact());
		response.setName("nome atualizado");
		Contact responseUpdate = service.save(response);
		assertNotNull(responseUpdate);
		assertEquals(responseUpdate.getName(), "nome atualizado");
		
		
	}
	
	@Test
	public void testUpdateFavorite() {

		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));
		Optional<Contact> contact = service.findById(getMockContact().getId());
		Contact c = contact.get();
		
		service.updateContactSetFavoriteForId(c.getId(), false);

		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));
		Optional<Contact> newContact = service.findById(c.getId());
		
		assertEquals(newContact.get().getFavorite(), false);

	}
	
	
	
	@Test(expected = java.lang.NullPointerException.class)
	public void testSaveWithoutPhone() {
		BDDMockito.given(repository.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		Contact c = getMockContact();
		c.setCellPhone(null);
		c.setCommercialPhone(null);
		c.setHomePhone(null);

		//repository.save(c);
		Contact response = service.save(c);
		assertNull(response);
		
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void testSaveWithoutEmail() {
		BDDMockito.given(repository.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		Contact c = getMockContact();
		c.setCommercialEmail(null);
		c.setPersonalEmail(null);
		//repository.save(c);

		Contact response = service.save(c);
		assertNull(response);
	}
	
	@Test
	public void testFindById() {
		
		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));
		Optional<Contact> response = service.findById(1L);
		
		assertNotNull(response);
		assertEquals(response.get().getName(), NAME);
	}
	
	@Test
	public void testFindByName() {
		
		List<Contact> list = new ArrayList<>();
		list.add(getMockContact());
		
		BDDMockito.given(repository.findByNameEquals(Mockito.anyString())).willReturn(list);
		List<Contact> response = service.findByNameEquals(NAME);
		
		assertNotNull(response);
		assertEquals(response.get(0).getName(), NAME);
	}
	
	@Test
	public void testFindAllPage() {

		List<Contact> list = new ArrayList<>();
		list.add(getMockContact());
		Page<Contact> page = new PageImpl(list);
		
		BDDMockito.given(repository.findAll(Mockito.any(PageRequest.class))).willReturn(page);
		
		Page<Contact> response = service.findAll(0);
		
		assertNotNull(response);
		assertEquals(response.getContent().size(), 1);
	
		
	}
	
	@Test
	public void testFindAll() {

		List<Contact> list = new ArrayList<>();
		list.add(getMockContact());
		
		BDDMockito.given(repository.findAll()).willReturn(list);
		
		List<Contact> response = service.findAll();
		
		assertNotNull(response);

		
	}
	
	@Test
	public void testDelete() {

		BDDMockito.given(repository.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		Contact c = service.save(getMockContact());
		
		service.deleteById(c.getId());
		
		Optional<Contact>  response = service.findById(c.getId());
		
		assertFalse(response.isPresent());
	}
	
	private Contact getMockContact() {
		Contact c = new Contact(1L, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE);
		return c;
	}

}
