package com.resttest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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

	private static final Long ID = 1L;
	private static final String NAME = "NomeTest";
	private static final String COMMERCIALPHONE = "1212-1212";
	private static final String HOMEPHONE = "1212-1212";
	private static final String CELLPHONE = "1212-1212";
	private static final String COMMERCIALEMAIL = "teste@teste.com.br";
	private static final String PERSONALEMAIL = "teste@teste.com.br";
	private static final Date DATEOFBIRTH = new Date();
	private static final LocalDate TODAY = LocalDate.now();
	private static final Boolean FAVORITE = true;
	private static final String URL = "/contact";

	@MockBean
	ContactService service;

	@Autowired
	MockMvc mvc;

	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.commercialPhone").value(COMMERCIALPHONE))
		.andExpect(jsonPath("$.data.homePhone").value(HOMEPHONE))
		.andExpect(jsonPath("$.data.cellPhone").value(CELLPHONE))
		.andExpect(jsonPath("$.data.commercialEmail").value(COMMERCIALEMAIL))
		.andExpect(jsonPath("$.data.personalEmail").value(PERSONALEMAIL))
		.andExpect(jsonPath("$.data.dateOfBirth").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.favorite").value(FAVORITE));

	}
	
	@Test
	public void testSaveInvalidContact() throws JsonProcessingException, Exception {
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, "email", DATEOFBIRTH, FAVORITE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors[0]").value("Email inválido"));
	}
	
	

	public Contact getMockContact() {
		Contact c = new Contact();
		c.setId(ID);
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

	public String getJsonPayload(Long id, String name, String commercialPhone, String homePhone, String cellphone,
			String commercialEmail, String personalEmail, Date dateOfBirth, boolean favorite)
			throws JsonProcessingException {
		ContactDTO dto = new ContactDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setCommercialPhone(commercialPhone);
		dto.setHomePhone(homePhone);
		dto.setCellPhone(cellphone);
		dto.setCommercialEmail(commercialEmail);
		dto.setPersonalEmail(personalEmail);
		dto.setDateOfBirth(dateOfBirth);
		dto.setFavorite(favorite);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
	public DateTimeFormatter getDateFormater() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return formatter;
	}
}
