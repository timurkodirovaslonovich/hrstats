package com.tim.hrstats.repositories

import com.tim.hrstats.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository



interface EmployeeRepository: JpaRepository<Employee, Long>