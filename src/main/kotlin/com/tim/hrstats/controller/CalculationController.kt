package com.tim.hrstats.controller

import com.tim.hrstats.dto.*
import com.tim.hrstats.service.CalculationService
import com.tim.hrstats.CalculationTypeEnum.CalculationType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/calculations")
class CalculationController(private val calculationService: CalculationService) {

    @PostMapping
    fun create(@RequestBody request: CalculationRequest): CalculationResponse =
        calculationService.createCalculation(request)

    @GetMapping("/employee/{employeeId}")
    fun getForEmployee(@PathVariable employeeId: Long): List<CalculationResponse> =
        calculationService.getCalculationsForEmployee(employeeId)


    @GetMapping("/summary")
    fun summaryByEmployee(
        @RequestParam year: Int,
        @RequestParam month: Int,
        @RequestParam type: CalculationType = CalculationType.SALARY
    ) = calculationService.summarizeByEmployeeForMonth(year, month, type)


    @GetMapping("/region-summary")
    fun regionSummary(
        @RequestParam year: Int,
        @RequestParam month: Int,
        @RequestParam regionId: Long
    ) = calculationService.regionSummaryPerEmployee(year, month, regionId)


    @GetMapping("/organization/{orgId}/average-salaries")
    fun avgPerEmployee(@PathVariable orgId: Long) =
        calculationService.averageSalaryPerEmployeeForOrganization(orgId)


    @GetMapping("/monthly-salary-vacation")
    fun salaryAndVacation(
        @RequestParam year: Int,
        @RequestParam month: Int
    ) = calculationService.salaryAndVacationPerEmployeeForMonth(year, month)
}
