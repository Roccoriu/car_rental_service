package org.rental.car_rental.service

import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalCreateMapper
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.RentalRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RentalService(private val rentalRepository: RentalRepository) {
    fun getAllRentals(): List<Rental> = rentalRepository.findAll()

    fun getRentalsById(id: Long): Optional<Rental> = rentalRepository.findById(id)

    fun createRental(rentalDto: RentalCreateDto): Rental {
        val rental = RentalCreateMapper.INSTANCE.dtoToCustomer(rentalDto)
        return rentalRepository.save(rental)
    }

    fun updateRental(rentalDto: RentalCreateDto): Rental {
        val rental = RentalCreateMapper.INSTANCE.dtoToCustomer(rentalDto)
        return rentalRepository.save(rental)
    }

    fun deleteRental(id: Long) = rentalRepository.deleteById(id)
}