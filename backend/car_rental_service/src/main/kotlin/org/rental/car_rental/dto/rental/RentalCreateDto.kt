package org.rental.car_rental.dto.rental

import jakarta.validation.constraints.*
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.model.Rental
import java.time.LocalDate

data class RentalCreateDto(
    @field:NotNull
    @field:Future
    val startDate: LocalDate,

    @field:NotNull
    @field:Future
    val endDate: LocalDate,

    @field:NotNull
    val customerId: Long,

    @field:NotNull
    val carId: Long
)