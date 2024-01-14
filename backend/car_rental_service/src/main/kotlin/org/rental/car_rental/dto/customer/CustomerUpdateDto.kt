package org.rental.car_rental.dto.customer

import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CustomerUpdateDto(
        @field:NotNull
        val id: Long,

        @field:NotNull
        val firstName: String,

        @field:NotNull
        val lastName: String,

        @field:NotNull
        val dateOfBirth: LocalDate,

        @field:NotNull
        val email: String,
)
