package org.rental.car_rental.dto.customer

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.model.Customer
import java.time.LocalDate

data class CustomerCreateDto(
        //var id: Long,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: LocalDate,
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
