package com.bean;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="appointment")
@Data
public class AppointmentBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer appointmnetId;
	
	@ManyToOne
	@JoinColumn(name = "providerId")
	ServiceProviderBean serviceprovider;
	
	@ManyToMany(mappedBy = "appointments")
	Set<UserBean> users;
	
	
}
