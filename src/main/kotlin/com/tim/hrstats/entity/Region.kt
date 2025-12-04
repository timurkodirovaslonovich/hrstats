package com.tim.hrstats.entity

import jakarta.persistence.Entity

@Entity
class Region(
    var name: String
): BaseEntity()