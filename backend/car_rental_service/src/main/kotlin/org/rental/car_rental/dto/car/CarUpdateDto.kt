package org.rental.car_rental.dto.car

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CarUpdateDto(
        @field:NotNull
        @field:Positive
        val id: Long,

        @field:NotNull
        val category: String,

        @field:NotNull
        val brand: String,

        @field:NotNull
        val model: String,

        @field:NotNull
        @field:Positive
        val year: Int,

        @field:NotNull
        val color: String,

        @field:NotNull
        @field:Positive
        val rentPriceDay: Double,

        @field:NotNull
        val isAutomatic: Boolean,

        @field:NotNull
        @field:Positive
        val seats: Int,

        @field:NotNull
        val image: String

)
