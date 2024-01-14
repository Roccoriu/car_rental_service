package org.rental.car_rental.dto.rental

import org.jetbrains.annotations.NotNull
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.model.Rental
import java.time.LocalDate

data class RentalCreateDto(
        @field:NotNull
        val startDate: LocalDate,

        @field:NotNull
        val endDate: LocalDate,

        @field:NotNull
        val customer: CustomerCreateDto,

        @field:NotNull
        val car: CarCreateUpdateDto
)

@Mapper
interface RentalCreateMapper {
    companion object {
        val INSTANCE: RentalCreateMapper = Mappers.getMapper(RentalCreateMapper::class.java)
    }

    fun customerToDto(rental: Rental): RentalCreateDto

    fun dtoToCustomer(rental: RentalCreateDto): Rental
}