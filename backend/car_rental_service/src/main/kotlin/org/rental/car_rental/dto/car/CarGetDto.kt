package org.rental.car_rental.dto.car

import org.mapstruct.Mapper
import org.rental.car_rental.model.Car
import java.time.LocalDate


data class RentalCarGetDto(
    var startDate: LocalDate = LocalDate.now(),
    var endDate: LocalDate = LocalDate.now()
)

data class CarGetDto(
    val category: String,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val rentPriceDay: Double,
    val isAutomatic: Boolean,
    val seats: Int,
    val image: String,
    val rentals: List<RentalCarGetDto>,
)

@Mapper(componentModel = "spring")
interface CarGetMapper {
    fun carToDto(car: Car): CarGetDto
}