package org.rental.car_rental.dto.car

import jakarta.validation.constraints.*
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

@Mapper(componentModel = "spring")
interface CarCreateUpdateMapper {
    fun dtoToCar(car: CarCreateUpdateDto): Car

}