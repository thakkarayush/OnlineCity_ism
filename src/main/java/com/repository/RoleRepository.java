package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.RoleBean;

public interface RoleRepository extends JpaRepository<RoleBean, Integer> {
	List<RoleBean> findAll();

	RoleBean findByRoleName(String roleName);

}
