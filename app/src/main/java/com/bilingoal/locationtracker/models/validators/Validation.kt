package com.bilingoal.locationtracker.models.validators

import com.bilingoal.locationtracker.dto.UserInput

interface Validation {
    fun validate(input: UserInput): Boolean
    fun validate(input: String): Boolean
    val type: Int
}