package com.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name="service_user")
@Data
public class UserBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@NotBlank(message = "Please enter First Name")
	private String firstName;
	
	@NotBlank(message = "Please enter Last Name")
	private String lastName;
	
//	@NotBlank(message = "Please enter Date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-mm-yyyy")
	private Date Dob;
	
	@NotBlank(message = "Please enter Gender")
	private String gender;
	
	@NotBlank(message = "Please enter Email")
	private String email;
	
//	@NotBlank(message = "Please enter Phone number")
	private String phone;
	
	@NotBlank(message = "Please enter password")
	private String password;
	
//	@NotBlank(message = "Please enter pincode")
	private Integer pincode;
	
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	RoleBean role;
	
	String authToken;
	
	private Integer otp;
	
}
