package com.tim.hrstats.service

import com.tim.hrstats.dto.*
import com.tim.hrstats.CalculationTypeEnum.CalculationType
import com.tim.hrstats.entity.Calculation
import com.tim.hrstats.repositories.CalculationRepository
import com.tim.hrstats.repositories.EmployeeRepository
import com.tim.hrstats.repositories.OrganizationRepository
import org.springframework.stereotype.Service



interface CalculationService {
    fun createCalculation(request: CalculationRequest): CalculationResponse
    fun getCalculationsForEmployee(employeeId: Long): List<CalculationResponse>

    // Task 2
    fun summarizeByEmployeeForMonth(year: Int, month: Int, type: CalculationType): List<EmployeeRateSummary>

    // Task 3
    fun regionSummaryPerEmployee(year: Int, month: Int, regionId: Long): List<EmployeeRegionSummary>

    // Task 4
    fun averageSalaryPerEmployeeForOrganization(orgId: Long): List<EmployeeOrgAverageSalary>

    // Task 5
    fun salaryAndVacationPerEmployeeForMonth(year: Int, month: Int): List<SalaryVacationPerEmployee>
}





@Service
class CalculationServiceImpl(
    private val calculationRepository: CalculationRepository,
    private val employeeRepository: EmployeeRepository,
    private val organizationRepository: OrganizationRepository
) : CalculationService {

    override fun createCalculation(request: CalculationRequest): CalculationResponse {
        val employee = employeeRepository.findById(request.employeeId)
            .orElseThrow { RuntimeException("Employee not found") }
        val organization = organizationRepository.findById(request.organizationId)
            .orElseThrow { RuntimeException("Organization not found") }

        val calc = Calculation(
            employee = employee,
            amount = request.amount,
            rate = request.rate!!,
            date = request.date,
            organization = organization,
            calculationType = request.calculationType
        )

        val saved = calculationRepository.save(calc)
        return CalculationResponse(
            id = saved.id,
            employeeId = saved.employee.id!!,
            amount = saved.amount,
            rate = saved.rate,
            date = saved.date,
            organizationId = saved.organization.id!!,
            calculationType = saved.calculationType.name
        )
    }

    override fun getCalculationsForEmployee(employeeId: Long): List<CalculationResponse> {
        return calculationRepository.findByEmployeeId(employeeId).map { c ->
            CalculationResponse(
                id = c.id,
                employeeId = c.employee.id!!,
                amount = c.amount,
                rate = c.rate,
                date = c.date,
                organizationId = c.organization.id!!,
                calculationType = c.calculationType.name
            )
        }
    }

    override fun summarizeByEmployeeForMonth(year: Int, month: Int, type: CalculationType): List<EmployeeRateSummary> {
        val ct = type
        return calculationRepository.summarizeByEmployeeForMonth(year, month, ct)
    }

    override fun regionSummaryPerEmployee(year: Int, month: Int, regionId: Long): List<EmployeeRegionSummary> {
        return calculationRepository.regionSummaryPerEmployee(year, month, regionId)
    }

    override fun averageSalaryPerEmployeeForOrganization(orgId: Long): List<EmployeeOrgAverageSalary> {
        return calculationRepository.averageSalaryPerEmployeeForOrganization(orgId)
    }

    override fun salaryAndVacationPerEmployeeForMonth(year: Int, month: Int): List<SalaryVacationPerEmployee> {
        return calculationRepository.salaryAndVacationPerEmployeeForMonth(year, month).map { r ->
            // `total` might be null if sums are null; ensure total computed
            val salary = r.salarySum ?: Double.NaN
            val vacation = r.vacationSum ?: Double.NaN
            val total = salary.plus(vacation)
            r.copy(total = total)
        }
    }
}
