package com.bilingoal.locationtracker.models.validators

import com.bilingoal.locationtracker.dto.UserInput
import javax.inject.Inject

class CredentialsValidator @Inject constructor() {
    private lateinit var set: Set<Validation>

    fun execute(input: UserInput, vararg validations: Validation): List<Int> {
        return setOf(*validations).filter { !it.validate(input) }.map { it.type }
    }

    fun execute(input: String, validation: Validation): Int {
        return if(!validation.validate(input)) { validation.type } else -1
    }
}