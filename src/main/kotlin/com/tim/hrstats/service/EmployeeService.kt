package com.tim.hrstats.service

import com.tim.hrstats.dto.CreateEmployeeRequest
import com.tim.hrstats.dto.CreateEmployeeResponse
import com.tim.hrstats.entity.Employee
import com.tim.hrstats.repositories.EmployeeRepository
import com.tim.hrstats.repositories.OrganizationRepository
import org.springframework.stereotype.Service


interface EmployeeService {
    fun createEmployee(request: CreateEmployeeRequest): CreateEmployeeResponse
    fun getEmployeeById(id: Long): CreateEmployeeResponse
    fun getAllEmployees(): List<CreateEmployeeResponse>
    fun updateEmployeeById(request: CreateEmployeeRequest, id: Long): CreateEmployeeResponse
    fun deleteEmployeeById(id: Long)
}



@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val organizationRepository: OrganizationRepository
) : EmployeeService {

    override fun createEmployee(request: CreateEmployeeRequest): CreateEmployeeResponse {

        val organization = organizationRepository.findById(request.organizationId)
            .orElseThrow { RuntimeException("Organization not found") }

        val employee = Employee(
            first_name = request.firstName,
            last_name = request.lastName,
            pinfl = request.pinfl,
            hire_date = request.hireDate,
            organization = organization
        )

        val saved = employeeRepository.save(employee)
        return mapToResponse(saved)
    }

    override fun getEmployeeById(id: Long): CreateEmployeeResponse {
        val employee: Employee? = employeeRepository.findById(id).orElse(null)

        return employee?.let { mapToResponse(it) }
            ?: throw RuntimeException("Employee not found")
    }

    override fun getAllEmployees(): List<CreateEmployeeResponse> {
        val employees: List<Employee> = employeeRepository.findAll()
        return employees.map { mapToResponse(it) }
    }

    override fun updateEmployeeById(request: CreateEmployeeRequest, id: Long): CreateEmployeeResponse {
        val oldEmployee = employeeRepository.findById(id).orElseThrow { RuntimeException("Employee not found") }
        oldEmployee.first_name = request.firstName
        oldEmployee.last_name = request.lastName
        oldEmployee.pinfl = request.pinfl
        oldEmployee.hire_date = request.hireDate
        val org = organizationRepository.findById(request.organizationId)
            .orElseThrow { RuntimeException("Organization not found") }
        oldEmployee.organization = org
        //val newEmployee = employeeRepository.save(oldEmployee)
        //return mapToResponse(newEmployee)


        return mapToResponse(employeeRepository.save(oldEmployee))

    }


    override fun deleteEmployeeById(id: Long) {
        if (employeeRepository.existsById(id)) return employeeRepository.deleteById(id)
        else throw RuntimeException("Employee not found")
    }






    private fun mapToResponse(e: Employee): CreateEmployeeResponse {
        return CreateEmployeeResponse(
            id = e.id!!,
            firstName = e.first_name,
            lastName = e.last_name,
            pinfl = e.pinfl,
            hireDate = e.hire_date,
            organizationId = e.organization.id!!,
            organizationName = e.organization.name
        )
    }
}

