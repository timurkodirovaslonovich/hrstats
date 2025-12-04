package com.tim.hrstats.service


import com.tim.hrstats.dto.CreateOrganizationRequest
import com.tim.hrstats.dto.CreateOrganizationResponse
import com.tim.hrstats.entity.Organization
import com.tim.hrstats.repositories.OrganizationRepository
import com.tim.hrstats.repositories.RegionRepository
import org.springframework.stereotype.Service


interface OrganizationService{
    fun createOrganization(request: CreateOrganizationRequest): CreateOrganizationResponse
    fun getOrganization(id:Long) : CreateOrganizationResponse
    fun getAllOrganizations(): List<CreateOrganizationResponse>
}


@Service
class OrganizationServiceImpl(
    val organizationRepository: OrganizationRepository,
    val regionRepository: RegionRepository,
): OrganizationService{
    override fun createOrganization(request: CreateOrganizationRequest): CreateOrganizationResponse {
        val region = regionRepository.findById(request.regionId).get()
        val organization = Organization(
            name = request.name,
            region = region
        )

        val saved = organizationRepository.save(organization)
        return mapToResponse(saved)
    }


    override fun getOrganization(id: Long): CreateOrganizationResponse {
        val organization = organizationRepository.findById(id).get()
        return mapToResponse(organization)
    }

    override fun getAllOrganizations(): List<CreateOrganizationResponse> {
        return organizationRepository.findAll().map { mapToResponse(it) }
    }



    private fun mapToResponse(o: Organization): CreateOrganizationResponse {
        return CreateOrganizationResponse(
            id = o.id!!,
            name = o.name,
            region = o.region?.id,
            parent = o.parent?.id

        )
    }
}