package com.tim.hrstats.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.util.Date


@Entity
class Employee(
    @Column(nullable = false)
    var first_name: String,
    @Column(nullable = false)
    var last_name: String,
    @Column(nullable = false)
    var pinfl: String,
    var hire_date: Date,
    @ManyToOne
    var organization: Organization
): BaseEntity()