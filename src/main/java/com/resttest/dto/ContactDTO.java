package com.resttest.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDTO {

	private Long id;
	@Length(min=3, max=50, message="O nome deve conter entre 3 a 50 caracteres")
	private String name;
	private String commercialPhone;
	private String homePhone;
	private String cellPhone;
	@Email(message="Email inválido")
	private String commercialEmail;
	@Email(message="Email inválido")
	private String personalEmail;
	@NotNull(message="Informe a data de nascimento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-Br", timezone = "Brazil/East")
	private Date dateOfBirth;
	private boolean favorite;
}
