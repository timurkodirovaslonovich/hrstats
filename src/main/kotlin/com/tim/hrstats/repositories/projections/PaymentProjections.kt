package com.tim.hrstats.repositories.projections

interface PaymentProjections {
    val pinfl: String
    val salary: Double?
    val vacation: Double?
    val pension: Double?
    val award: Double?
}