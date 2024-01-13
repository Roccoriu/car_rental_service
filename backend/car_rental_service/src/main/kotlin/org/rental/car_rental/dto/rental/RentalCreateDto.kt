package org.rental.car_rental.dto.rental

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.dto.car.CarCreateDto
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import org.rental.car_rental.model.Rental
import java.time.LocalDate

data class RentalCreateDto(
        val startDate: LocalDate,
        val endDate: LocalDate,
        val customer: CustomerCreateDto,
        val car: CarCreateDto
)

@Mapper
interface RentalCreateMapper {
    companion object {
        val INSTANCE: RentalCreateMapper = Mappers.getMapper(RentalCreateMapper::class.java)
    }

    fun customerToDto(rental: Rental): RentalCreateDto

    fun dtoToCustomer(rental: RentalCreateDto): Rental
}