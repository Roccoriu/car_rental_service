package org.rental.car_rental.dto.customer

import org.mapstruct.Mapper
import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import java.time.LocalDate

data class CustomerRentalCarGetDto(
    var startDate: LocalDate = LocalDate.now(),
    var endDate: LocalDate = LocalDate.now(),
    var car: Car? = null,
)

data class CustomerGetDto(
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDate? = LocalDate.now(),
    val email: String = "",
    val rentals: List<CustomerRentalCarGetDto> = mutableListOf(),
)


@Mapper(componentModel = "spring")
interface CustomerGetMapper {
    fun customerToDto(customer: Customer): CustomerGetDto
}