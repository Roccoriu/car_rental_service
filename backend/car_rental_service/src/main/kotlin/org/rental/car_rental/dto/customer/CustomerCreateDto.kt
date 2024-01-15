package org.rental.car_rental.dto.customer

import jakarta.validation.constraints.*
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.model.Customer
import java.time.LocalDate

data class CustomerCreateDto(
        @field:NotNull
        val firstName: String,

        @field:NotNull
        val lastName: String,

        @field:NotNull
        @field:Future
        val dateOfBirth: LocalDate,

        @field:NotNull
        @field:Email
        val email: String,
)

@Mapper
interface CustomerCreateMapper {
    companion object {
        val INSTANCE: CustomerCreateMapper = Mappers.getMapper(CustomerCreateMapper::class.java)
    }

    fun customerToDto(customer: Customer): CustomerCreateDto
    fun dtoToCustomer(customer: CustomerCreateDto): Customer
}
