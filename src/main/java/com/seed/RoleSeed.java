//package com.seed;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.bean.RoleBean;
//import com.repository.RoleRepository;
//
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Component
//public class RoleSeed {
//	@Autowired
//	RoleRepository roleRepository;
//
//	@PostConstruct //used on a method that needs to be executed after dependency injection is done to perform any initialization.
//	void createRole() {
//		RoleBean role = roleRepository.findByRoleName("user");
//		if(role == null) {
//			RoleBean role1 = new RoleBean();
//			role1.setRoleName("service user");
//			roleRepository.save(role1);
//			
//			RoleBean role2 = new RoleBean();
//			role2.setRoleName("admin");
//			roleRepository.save(role2);
//			
//			RoleBean role3 = new RoleBean();
//			role3.setRoleName("service provider");
//			roleRepository.save(role3);
//			log.info("Role Added....");
//			
//		} else {
//			log.info("Role already added...");
//		}
//	}
//}
