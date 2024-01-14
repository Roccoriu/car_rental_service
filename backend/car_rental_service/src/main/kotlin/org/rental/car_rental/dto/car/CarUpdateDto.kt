package org.rental.car_rental.dto.car

import jakarta.validation.constraints.NotNull

data class CarUpdateDto(
        @field:NotNull
        val id: Long,

        @field:NotNull
        val category: String,

        @field:NotNull
        val brand: String,

        @field:NotNull
        val model: String,

        @field:NotNull
        val year: Int,

        @field:NotNull
        val color: String,

        @field:NotNull
        val rentPriceDay: Double,

        @field:NotNull
        val isAutomatic: Boolean,

        @field:NotNull
        val seats: Int,

        @field:NotNull
        val image: String
)
