package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.AppointmentBean;

public interface AppointmentRepository extends JpaRepository<AppointmentBean, Integer> {

}
