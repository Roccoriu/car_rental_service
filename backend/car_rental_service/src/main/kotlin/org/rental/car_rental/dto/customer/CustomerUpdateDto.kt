package org.rental.car_rental.dto.customer

import jakarta.validation.constraints.*
import java.time.LocalDate

data class CustomerUpdateDto(
        @field:NotNull
        @field:Positive
        val id: Long,

        @field:NotNull
        val firstName: String,

        @field:NotNull
        val lastName: String,

        @field:NotNull
        @field:Future
        val dateOfBirth: LocalDate,

        @field:NotNull
        val email: String,
)
