package com.resttest.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4250015080989805664L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="commercial_phone", nullable = true)
	private String commercialPhone;
	@Column(name="home_phone", nullable = true)
	private String homePhone;
	@Column(name="cell_phone", nullable = true)
	private String cellPhone;
	@Column(name="commercial_email", nullable = true)
	private String commercialEmail;
	@Column(name="personal_email", nullable = true)
	private String personalEmail;
	@Column(name="date_of_birth", nullable = true)
	private Date dateOfBirth;
	//@Type(type="true_false")
	@Column(name="favorite", nullable = false)
	private Boolean favorite;
	
	
	public List<String> validateFields(Contact c) {
		List<String> listError = new ArrayList<String>();

		
		if (c.getCellPhone() == null && c.getCommercialPhone() == null && c.getHomePhone() == null) {
			listError.add("Necessário preencher um Telefone");	
		}

		if (c.getCommercialEmail() == null && c.getPersonalEmail() == null) {
			listError.add("Necessário preencher um Email");	
	    }
	
		return listError;
	}

}

