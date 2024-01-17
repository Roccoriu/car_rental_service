package org.rental.car_rental.dto.rental

import java.time.LocalDate

import org.mapstruct.Mapper

import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.model.Rental

data class RentalGetDto(
    var startDate: LocalDate = LocalDate.now(),
    var endDate: LocalDate = LocalDate.now(),
    var customer: Customer? = null,
    var car: Car? = null,
)


@Mapper(componentModel = "spring")
interface RentalGetMapper {
    fun rentalToDto(car: Rental): RentalGetDto
}