package org.rental.car_rental.service

import jakarta.persistence.EntityNotFoundException
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalCreateMapper
import org.rental.car_rental.dto.rental.RentalUpdateDto
import org.rental.car_rental.dto.rental.RentalUpdateMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(private val rentalRepository: RentalRepository) {
    fun getAllRentals(): List<Rental> = rentalRepository.findAll()

    fun getRentalsById(id: Long): Rental = rentalRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

    fun createRental(rentalDto: RentalCreateDto): Rental {
        val rental = RentalCreateMapper.INSTANCE.dtoToRental(rentalDto)
        return rentalRepository.save(rental)
    }

    fun updateRental(id: Long, rentalDto: RentalUpdateDto): Rental {
        rentalRepository
                .findById(id)
                .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

        val rental = RentalUpdateMapper.INSTANCE.dtoToRental(rentalDto)

        rental.id = id
        return rentalRepository.save(rental)
    }

    fun deleteRental(id: Long) = rentalRepository.deleteById(id)
}