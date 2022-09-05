package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.ServiceProviderBean;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderBean, Integer> {
	ServiceProviderBean findByEmail(String email);
}
