package com.tim.hrstats.entity

import com.tim.hrstats.CalculationTypeEnum.CalculationType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import java.util.Date


@Entity
class Calculation(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    var employee: Employee,
    @Column(nullable = false)
    var amount: Double,
    @Column(nullable = false)
    var rate: Double,
    var date: Date,
    @ManyToOne
    @JoinColumn(name = "parent_id")
    var organization: Organization,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var calculationType: CalculationType

    ): BaseEntity()