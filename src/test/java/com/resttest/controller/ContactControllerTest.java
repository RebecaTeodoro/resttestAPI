package com.resttest.controller;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resttest.dto.ContactDTO;
import com.resttest.entity.Contact;
import com.resttest.service.ContactService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContactControllerTest {
	
	private static final String NAME = "NomeTest";
	private static final String COMMERCIALPHONE = "1212-1212";
	private static final String HOMEPHONE = "1212-1212";
	private static final String CELLPHONE = "1212-1212";
	private static final String COMMERCIALEMAIL =  "teste@teste.com.br";
	private static final String PERSONALEMAIL  = "teste@teste.com.br";
	private static final Date  DATEOFBIRTH = new Date(); 
	private static final Boolean FAVORITE = true;
	private static final String URL = "/contact";
	
	
	@MockBean
	ContactService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(getMockUser());
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	public Contact getMockUser() {
		Contact c = new Contact();
		c.setName(NAME);
		c.setCommercialPhone(COMMERCIALPHONE);
		c.setHomePhone(HOMEPHONE);
		c.setCellPhone(CELLPHONE);
		c.setCommercialEmail(COMMERCIALEMAIL);
		c.setPersonalEmail(PERSONALEMAIL);
		c.setDateOfBirth(DATEOFBIRTH);
		c.setFavorite(FAVORITE);
		
		return c;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		ContactDTO dto = new ContactDTO();
		dto.setName(NAME);
		dto.setCommercialPhone(COMMERCIALPHONE);
		dto.setHomePhone(HOMEPHONE);
		dto.setCellPhone(CELLPHONE);
		dto.setCommercialEmail(COMMERCIALEMAIL);
		dto.setPersonalEmail(PERSONALEMAIL);
		dto.setDateOfBirth(DATEOFBIRTH);
		dto.setFavorite(FAVORITE);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
}


