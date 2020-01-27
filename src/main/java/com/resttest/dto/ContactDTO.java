package com.resttest.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDTO {

	private Long id;
	@Length(min=3, max=50, message="O nome deve conter entre 3 a 50 caracteres")
	@NotNull(message = "O campo nome é obrigatório")
	private String name;
	@Pattern(regexp="(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
	private String commercialPhone;
	@Pattern(regexp="(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
	private String homePhone;
	@Pattern(regexp="(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})")
	private String cellPhone;
	@Email(message="O campo Email está inválido")
	private String commercialEmail;
	@Email(message="O campo Email está inválido")
	private String personalEmail;
	@NotNull(message="Informe a data de nascimento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-Br", timezone = "Brazil/East")
	private Date dateOfBirth;
	@NotNull(message = "O campo favorito é obrigatório")
	private Boolean favorite;
	
	
}
