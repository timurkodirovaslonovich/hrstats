package com.tim.hrstats.dto


import com.tim.hrstats.CalculationTypeEnum.CalculationType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.Date


data class CreateEmployeeRequest(
    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    val pinfl: String,


    //Not:Blank is not available for Date data type, use NotNull instead.

    @field:NotNull
    var hireDate: Date,
    val organizationId: Long
)


data class CreateEmployeeResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val pinfl: String,
    val hireDate: Date,
    val organizationId: Long,
    val organizationName: String
)


data class CreateOrganizationRequest(
    val name: String,
    val regionId: Long,
    val parentId: Long? = null
)


data class CreateOrganizationResponse(
    val id: Long,
    val name: String,
    val region: Long?,
    val parent: Long? = null,
)


data class CreateRegionRequest(
    @field:NotBlank
    val name: String
)

data class CreateRegionResponse(
    val id: Long,
    val name: String
)




data class CalculationRequest(
    val employeeId: Long,
    val amount: Double,
    val rate: Double? = null,
    val date: Date,
    val organizationId: Long,
    val calculationType: CalculationType
)



data class CalculationResponse(
    val id: Long?,
    val employeeId: Long,
    val amount: Double,
    val rate: Double?,
    val date: Date,
    val organizationId: Long,
    val calculationType: String
)


data class EmployeeRateSummary(
    val pinfl: String,
    val totalAmount: Double?,
    val avgRate: Double?
)


data class EmployeeRegionSummary(
    val pinfl: String,
    val distinctOrganizations: Long,
    val totalAmount: Double?
)


data class EmployeeOrgAverageSalary(
    val employeeId: Long,
    val firstName: String,
    val lastName: String,
    val averageMonthlySalary: Double?
)



data class SalaryVacationPerEmployee(
    val employeeId: Long,
    val firstName: String,
    val lastName: String,
    val salarySum: Double?,
    val vacationSum: Double?,
    val total: Double?
)