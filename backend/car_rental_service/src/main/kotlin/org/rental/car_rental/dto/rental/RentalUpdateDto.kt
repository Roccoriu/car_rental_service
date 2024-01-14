package org.rental.car_rental.dto.rental

import jakarta.validation.constraints.Future
import org.jetbrains.annotations.NotNull
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.dto.car.CarUpdateDto
import org.rental.car_rental.dto.customer.CustomerUpdateDto
import org.rental.car_rental.model.Rental
import java.time.LocalDate


data class RentalUpdateDto(
        @field:NotNull
        @field:Future
        val startDate: LocalDate,

        @field:NotNull
        @field:Future
        val endDate: LocalDate,

        @field:NotNull
        val customer: CustomerUpdateDto,

        @field:NotNull
        val car: CarUpdateDto
)

@Mapper
interface RentalUpdateMapper {
    companion object {
        val INSTANCE: RentalUpdateMapper = Mappers.getMapper(RentalUpdateMapper::class.java)
    }

    fun rentalToDto(rental: Rental): RentalUpdateDto
    fun dtoToRental(rental: RentalUpdateDto): Rental
}
