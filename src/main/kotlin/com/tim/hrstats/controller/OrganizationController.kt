package com.tim.hrstats.controller


import com.tim.hrstats.dto.*
import com.tim.hrstats.service.OrganizationService
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/organizations")
@Tag(name = "Organization", description = "Organizations management APIs")
class OrganizationController(
    private val organizationService: OrganizationService
) {

    @PostMapping
    @Operation(summary = "Add new organization")
    fun addNewOrganization(@Valid @RequestBody request: CreateOrganizationRequest): ResponseEntity<CreateOrganizationResponse> {
        val organization = organizationService.createOrganization(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(organization)
    }

    @GetMapping
    @Operation(summary = "Getting all organizations")
    fun getAllOrganizations(): ResponseEntity<List<CreateOrganizationResponse>> {
        val organizations = organizationService.getAllOrganizations()
        return ResponseEntity.ok(organizations)
    }



}