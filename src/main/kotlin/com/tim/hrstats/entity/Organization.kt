package com.tim.hrstats.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType

import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne


@Entity
class Organization(

    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    var region: Region?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Organization? = null,


) : BaseEntity()
