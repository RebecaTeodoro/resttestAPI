package com.resttest.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Contact implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4250015080989805664L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	private String commercialPhone;
	private String homePhone;
	private String cellPhone;
	private String commercialEmail;
	private String personalEmail;
	private Date dateOfBirth;
	private boolean favorite;
	
	

}
