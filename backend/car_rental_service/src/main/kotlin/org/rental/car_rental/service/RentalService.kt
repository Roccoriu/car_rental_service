package org.rental.car_rental.service

import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.RentalRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RentalService(private val rentalRepository: RentalRepository) {
    fun getAllRentals(): List<Rental> = rentalRepository.findAll()

    fun getCarsById(id: Long): Optional<Rental> = rentalRepository.findById(id)
}