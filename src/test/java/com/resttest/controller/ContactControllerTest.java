package com.resttest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
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
	public void testFindByName() throws Exception {
		
		List<Contact> list = new ArrayList<>();
		list.add(getMockContact());
		
		BDDMockito.given(service.findByNameEquals(Mockito.anyString())).willReturn(list);
		
		mvc.perform(MockMvcRequestBuilders.get(URL + "/name/name=" + NAME)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.[0].id").value(ID))
		.andExpect(jsonPath("$.data.[0].name").value(NAME))
		.andExpect(jsonPath("$.data.[0].commercialPhone").value(COMMERCIALPHONE))
		.andExpect(jsonPath("$.data.[0].homePhone").value(HOMEPHONE))
		.andExpect(jsonPath("$.data.[0].cellPhone").value(CELLPHONE))
		.andExpect(jsonPath("$.data.[0].commercialEmail").value(COMMERCIALEMAIL))
		.andExpect(jsonPath("$.data.[0].personalEmail").value(PERSONALEMAIL))
		.andExpect(jsonPath("$.data.[0].dateOfBirth").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.[0].favorite").value(FAVORITE));
	}
	
	@Test
	public void testFindById() throws Exception {
		//verificar como o BDDMockito lida com os retornos ID/String
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));

		mvc.perform(MockMvcRequestBuilders.get(URL + "/id="+ID)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
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
	public void testFindAll() throws Exception {
		List<Contact> lst = new ArrayList<Contact>();
		lst.add(getMockContact());
		
		BDDMockito.given(service.findAll()).willReturn(lst);

		mvc.perform(MockMvcRequestBuilders.get(URL)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.[0].id").value(ID))
		.andExpect(jsonPath("$.data.[0].name").value(NAME))
		.andExpect(jsonPath("$.data.[0].commercialPhone").value(COMMERCIALPHONE))
		.andExpect(jsonPath("$.data.[0].homePhone").value(HOMEPHONE))
		.andExpect(jsonPath("$.data.[0].cellPhone").value(CELLPHONE))
		.andExpect(jsonPath("$.data.[0].commercialEmail").value(COMMERCIALEMAIL))
		.andExpect(jsonPath("$.data.[0].personalEmail").value(PERSONALEMAIL))
		.andExpect(jsonPath("$.data.[0].dateOfBirth").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.[0].favorite").value(FAVORITE));
	}
	
	@Test
	public void testFindAllPage() throws Exception {
		
		List<Contact> list = new ArrayList<>();
		list.add(getMockContact());
		
		Page<Contact> page = new PageImpl(list);
		
		BDDMockito.given(service.findAll(Mockito.anyInt())).willReturn(page);

		mvc.perform(MockMvcRequestBuilders.get(URL+"/page/page="+0)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.content[0].id").value(ID))
		.andExpect(jsonPath("$.data.content[0].name").value(NAME))
		.andExpect(jsonPath("$.data.content[0].commercialPhone").value(COMMERCIALPHONE))
		.andExpect(jsonPath("$.data.content[0].homePhone").value(HOMEPHONE))
		.andExpect(jsonPath("$.data.content[0].cellPhone").value(CELLPHONE))
		.andExpect(jsonPath("$.data.content[0].commercialEmail").value(COMMERCIALEMAIL))
		.andExpect(jsonPath("$.data.content[0].personalEmail").value(PERSONALEMAIL))
		.andExpect(jsonPath("$.data.content[0].dateOfBirth").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.content[0].favorite").value(FAVORITE));
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		String newName = "Novo nome";


		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(new Contact(1L, newName, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE));
		
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(newName))
		.andExpect(jsonPath("$.data.commercialPhone").value(COMMERCIALPHONE))
		.andExpect(jsonPath("$.data.homePhone").value(HOMEPHONE))
		.andExpect(jsonPath("$.data.cellPhone").value(CELLPHONE))
		.andExpect(jsonPath("$.data.commercialEmail").value(COMMERCIALEMAIL))
		.andExpect(jsonPath("$.data.personalEmail").value(PERSONALEMAIL))
		.andExpect(jsonPath("$.data.dateOfBirth").value(TODAY.format(getDateFormater())))
		.andExpect(jsonPath("$.data.favorite").value(FAVORITE));
		
	}
	
	@Test
	public void testUpdateFavorite() throws Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(getMockContact()));
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(new Contact(1L, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, false));
		
		
		mvc.perform(MockMvcRequestBuilders.put(URL+ "/updateFavorite/").content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value("Contato de id: "+ 1L +" atualizado com sucesso"));
		
		
		
	}
	
	@Test
	public void testUpdateInvalidId() throws Exception {
		
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("Contato não encontrado"));
		
	}
	
	@Test
	public void testSaveInvalidContactPhone() throws JsonProcessingException, Exception {
		

		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(new Contact(1L, NAME, null, null, null, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE));
		
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(1L, NAME, null, null, null, COMMERCIALEMAIL, PERSONALEMAIL, DATEOFBIRTH, FAVORITE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("Necessário preencher um Telefone"));
	}
	
	@Test
	public void testSaveInvalidContactEmail() throws JsonProcessingException, Exception {
		
	
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(new Contact(1L, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, null, null, DATEOFBIRTH, FAVORITE));
		
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(1L, NAME, COMMERCIALPHONE, HOMEPHONE, CELLPHONE, null, null, DATEOFBIRTH, FAVORITE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("Necessário preencher um Email"));
	}
	

	
	@Test
	public void testDeleteInvalidId() throws JsonProcessingException, Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());
		
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/99")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.data").doesNotExist())
		.andExpect(jsonPath("$.errors[0]").value("Contato não encontrado"));
	}
	
	@Test
	public void testDeleteById() throws JsonProcessingException, Exception {
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(new Contact()));
		
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value("Contato de id: "+ ID +" apagado com sucesso"));
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

	public String getJsonPayload() throws JsonProcessingException {
		ContactDTO dto = new ContactDTO();
		dto.setId(ID);
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
