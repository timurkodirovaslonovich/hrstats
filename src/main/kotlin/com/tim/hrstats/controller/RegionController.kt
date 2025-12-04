package com.tim.hrstats.controller

import com.tim.hrstats.dto.CreateRegionRequest
import com.tim.hrstats.dto.CreateRegionResponse
import com.tim.hrstats.service.RegionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/regions")
@Tag(name = "Regions", description = "regions management api for CRUD operations")
class RegionController(
    private val regionService: RegionService
) {



    @PostMapping
    @Operation(method = "create", summary = "Create a new region")
    fun createRegion(@Valid @RequestBody createRegionRequest: CreateRegionRequest):
            ResponseEntity<CreateRegionResponse> {
                val region = regionService.createRegion(createRegionRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(region)
    }



    @GetMapping
    @Operation(method = "getAll", summary = "Get all regions")
    fun getAllRegions(): ResponseEntity<List<CreateRegionResponse>> {
        return ResponseEntity.ok(regionService.findAllRegions())
    }

    @GetMapping("/{id}")
    @Operation(method = "getById", summary = "Get region by id")
    fun getRegionById(@PathVariable id: Long)
            : ResponseEntity<CreateRegionResponse> =
        ResponseEntity.ok(regionService.findRegionById(id))


    @PutMapping("/{id}")
    @Operation(method = "update", summary = "Update region")
    fun updateRegionById(@PathVariable id: Long, @Valid @RequestBody requestBody: CreateRegionRequest):
            ResponseEntity<CreateRegionResponse>{
        return ResponseEntity.ok(regionService.updateRegionById(requestBody, id))
    }



    @DeleteMapping("/{id}")
    @Operation(method = "deleteById", summary = "Delete region by id")
    fun deleteRegionById(@PathVariable id: Long): ResponseEntity<Unit> {
        regionService.deleteRegionById(id)
        return ResponseEntity.noContent().build()
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun deleteRegionById(@PathVariable id: Long) =
//        regionService.deleteRegionById(id)

}