package org.rental.car_rental.service

import jakarta.persistence.EntityNotFoundException
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalCreateMapper
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.RentalRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RentalService(private val rentalRepository: RentalRepository) {
    fun getAllRentals(): List<Rental> = rentalRepository.findAll()

    fun getRentalsById(id: Long): Rental = rentalRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("Item not found with id: $id") }

    fun createRental(rentalDto: RentalCreateDto): Rental {
        val rental = RentalCreateMapper.INSTANCE.dtoToCustomer(rentalDto)
        return rentalRepository.save(rental)
    }

    fun updateRental(id: Long, rentalDto: RentalCreateDto): Rental {
        rentalRepository
                .findById(id)
                .orElseThrow { EntityNotFoundException("Item not found with id: $id") }

        val rental = RentalCreateMapper.INSTANCE.dtoToCustomer(rentalDto)

        rental.id = id
        return rentalRepository.save(rental)
    }

    fun deleteRental(id: Long) = rentalRepository.deleteById(id)
}