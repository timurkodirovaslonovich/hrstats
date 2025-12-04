package com.tim.hrstats.repositories


import com.tim.hrstats.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Date

interface RegionRepository: JpaRepository<Region, Long>{
    fun findRegionByName(name: String): Region?
    fun findAllByCreatedDate(date: Date): List<Region>
}