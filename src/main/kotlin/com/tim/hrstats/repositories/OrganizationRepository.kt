package com.tim.hrstats.repositories

import com.tim.hrstats.entity.Organization
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationRepository: JpaRepository<Organization, Long>