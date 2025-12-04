package com.tim.hrstats.service

import com.tim.hrstats.dto.CreateRegionRequest
import com.tim.hrstats.dto.CreateRegionResponse
import com.tim.hrstats.entity.Region
import com.tim.hrstats.repositories.RegionRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.Date
import java.time.LocalDate



interface RegionService {
    fun createRegion(request: CreateRegionRequest): CreateRegionResponse
    fun findAllRegions(): List<CreateRegionResponse>
    fun findRegionById(id: Long): CreateRegionResponse
    fun updateRegionById(request: CreateRegionRequest, id: Long): CreateRegionResponse
    fun deleteRegionById(id: Long)
}



@Service
class RegionServiceImpl(
    private val regionRepository: RegionRepository
): RegionService {



    //Creating Region method
    override fun createRegion(request: CreateRegionRequest): CreateRegionResponse {
        val region = Region(
            name = request.name,
        )

        val saved = regionRepository.save(region)

        return mapToResponse(saved)
    }

    //Getting all the Regions
    override fun findAllRegions(): List<CreateRegionResponse> {
        val regions = regionRepository.findAll()
        return regions.map { mapToResponse(it) }
    }


    // getting single region by ID

    override fun findRegionById(id: Long): CreateRegionResponse {
        val region = regionRepository.findById(id)
            .orElseThrow{ EntityNotFoundException("Region with id $id not found") }

        return mapToResponse(region)
    }


    //updating the region by id

    override fun updateRegionById(request: CreateRegionRequest, id: Long): CreateRegionResponse {
        val oldRegion = regionRepository.findById(id)
        .orElseThrow{ EntityNotFoundException("Region with id $id not found") }

        oldRegion.name = request.name
        oldRegion.modifiedDate = LocalDate.now() as Date
        val updatedRegion = regionRepository.save(oldRegion)

        return mapToResponse(updatedRegion)

    }


    override fun deleteRegionById(id: Long) {
        //checking if the region with given id exists
        if(!regionRepository.existsById(id)) throw EntityNotFoundException("Region with id $id not found")


        // JpaRepository.deleteById returns Unit(void) type
        return regionRepository.deleteById(id)
    }





    //method for mapping saved entity to response dto and returning

    private fun mapToResponse(region: Region): CreateRegionResponse {
        return CreateRegionResponse(
            id = region.id!!,
            name = region.name,
        )
    }
}