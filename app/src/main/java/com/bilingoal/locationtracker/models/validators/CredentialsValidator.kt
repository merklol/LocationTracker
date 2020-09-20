package com.bilingoal.locationtracker.models.validators

import com.bilingoal.locationtracker.dto.UserInput

class CredentialsValidator() {
    private lateinit var set: Set<Validation>

    constructor(set: Set<Validation>) : this() {
        this.set = set
    }

    fun execute(input: UserInput, vararg validations: Validation): List<Int> {
        return setOf(*validations).filter { !it.validate(input) }.map { it.type }
    }

    fun execute(input: String, validation: Validation): Int {
        return if(!validation.validate(input)) { validation.type } else -1
    }
}