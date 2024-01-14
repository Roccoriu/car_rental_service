package org.rental.car_rental.dto.car

import org.jetbrains.annotations.NotNull
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.model.Car

data class CarCreateUpdateDto(
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

@Mapper
interface CarCreateUpdateMapper {
    fun carToDto(car: Car): CarCreateUpdateDto
    fun dtoToCar(car: CarCreateUpdateDto): Car

    companion object {
        val INSTANCE: CarCreateUpdateMapper = Mappers.getMapper(CarCreateUpdateMapper::class.java)
    }
}