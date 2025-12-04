package com.tim.hrstats.controller


import com.tim.hrstats.service.EmployeeService
import com.tim.hrstats.dto.*
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee", description = "Employees management APIs")
class EmployeeController(
    private val employeeService: EmployeeService
) {


    //CREATE
    @PostMapping
    @Operation(summary = "Add new employee")
    fun createNewEmployee(@Valid @RequestBody request: CreateEmployeeRequest): ResponseEntity<CreateEmployeeResponse> {
        val employee = employeeService.createEmployee(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(employee)
    }


    //READ
    @GetMapping
    @Operation(summary = "Getting all employees")
    fun getAllEmployees(): ResponseEntity<List<CreateEmployeeResponse>> {
        val employees = employeeService.getAllEmployees()
        return ResponseEntity.ok(employees)
    }


    @GetMapping("/{id}")
    @Operation(method = "GET", summary = "Get employee by id")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<CreateEmployeeResponse> {
        val employee = employeeService.getEmployeeById(id)
        return ResponseEntity.ok(employee)
    }

//Kotlin friendly single line function controller
//    @GetMapping("/{id}")
//    @Operation(method = "GET", summary = "Get employee by id")
//    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<CreateEmployeeResponse> =
//        ResponseEntity.ok(employeeService.getEmployeeById(id))



    //UPDATE

    @PutMapping("/{id}")
    @Operation(method = "PUT", summary = "Updating employee by id")
    fun updateEmployeeById(@Valid @RequestBody request: CreateEmployeeRequest,@PathVariable id: Long) {
        ResponseEntity.ok(employeeService.updateEmployeeById(request, id))
    }


    //DELETE


    @DeleteMapping("/{id}")
    @Operation(method="DELETE", summary = "deleting employee by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEmployeeById(@PathVariable id: Long)
    = employeeService.deleteEmployeeById(id)

}