package com.tim.hrstats.repositories

import com.tim.hrstats.dto.*
import com.tim.hrstats.entity.Calculation
import com.tim.hrstats.CalculationTypeEnum.CalculationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CalculationRepository : JpaRepository<Calculation, Long> {

    // Basic filters
    fun findByEmployeeId(employeeId: Long): List<Calculation>
    fun findByOrganizationId(orgId: Long): List<Calculation>

    // ------------------------------------------------------------
    // 1) Summary per employee for specific month
    // ------------------------------------------------------------
    @Query("""
        SELECT new com.tim.hrstats.dto.EmployeeRateSummary(
            e.pinfl,
            SUM(c.amount),
            AVG(c.rate)
        )
        FROM Calculation c JOIN c.employee e
        WHERE EXTRACT(YEAR FROM c.date) = :year
          AND EXTRACT(MONTH FROM c.date) = :month
          AND c.calculationType = :type
        GROUP BY e.pinfl
    """)
    fun summarizeByEmployeeForMonth(
        @Param("year") year: Int,
        @Param("month") month: Int,
        @Param("type") type: CalculationType
    ): List<EmployeeRateSummary>

    // ------------------------------------------------------------
    // 2) Region summary: distinct org count + total salary
    // ------------------------------------------------------------
    @Query("""
        SELECT new com.tim.hrstats.dto.EmployeeRegionSummary(
            e.pinfl,
            COUNT(DISTINCT c.organization.id),
            SUM(c.amount)
        )
        FROM Calculation c
          JOIN c.employee e
          JOIN c.organization o
          JOIN o.region r
        WHERE EXTRACT(YEAR FROM c.date) = :year
          AND EXTRACT(MONTH FROM c.date) = :month
          AND r.id = :regionId
          AND c.calculationType = :type
        GROUP BY e.pinfl
    """)
    fun regionSummaryPerEmployee(
        @Param("year") year: Int,
        @Param("month") month: Int,
        @Param("regionId") regionId: Long,
        @Param("type") type: CalculationType = CalculationType.SALARY
    ): List<EmployeeRegionSummary>

    // ------------------------------------------------------------
    // 3) Org summary: employees + average salary
    // ------------------------------------------------------------
    @Query("""
        SELECT new com.tim.hrstats.dto.EmployeeOrgAverageSalary(
           e.id,
           e.first_name,
           e.last_name,
           AVG(c.amount)
        )
        FROM Calculation c
          JOIN c.employee e
        WHERE c.organization.id = :orgId
          AND c.calculationType = :type
        GROUP BY e.id, e.first_name, e.last_name
    """)
    fun averageSalaryPerEmployeeForOrganization(
        @Param("orgId") orgId: Long,
        @Param("type") type: CalculationType = CalculationType.SALARY
    ): List<EmployeeOrgAverageSalary>

    // ------------------------------------------------------------
    // 4) Salary + Vacation per employee for a month
    // ------------------------------------------------------------
    @Query("""
        SELECT new com.tim.hrstats.dto.SalaryVacationPerEmployee(
            e.id,
            e.first_name,
            e.last_name,
            SUM(CASE WHEN c.calculationType = com.tim.hrstats.CalculationTypeEnum.CalculationType.SALARY THEN c.amount ELSE 0 END),
            SUM(CASE WHEN c.calculationType = com.tim.hrstats.CalculationTypeEnum.CalculationType.VACATION THEN c.amount ELSE 0 END),
            SUM(c.amount)
        )
        FROM Calculation c
          JOIN c.employee e
        WHERE EXTRACT(YEAR FROM c.date) = :year
          AND EXTRACT(MONTH FROM c.date) = :month
        GROUP BY e.id, e.first_name, e.last_name
    """)
    fun salaryAndVacationPerEmployeeForMonth(
        @Param("year") year: Int,
        @Param("month") month: Int
    ): List<SalaryVacationPerEmployee>
}
