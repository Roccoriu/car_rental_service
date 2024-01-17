package org.rental.car_rental.service

import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalUpdateDto
import org.rental.car_rental.dto.rental.RentalUpdateMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.repository.CustomerRepository
import org.rental.car_rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository
) {
    fun getAllRentals(): List<Rental> = rentalRepository.findAll()

    fun getRentalsById(id: Long): Rental = rentalRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

    fun createRental(rentalDto: RentalCreateDto): Rental {
        val car = carRepository.findById(rentalDto.carId)
            .orElseThrow { ResourceNotFoundException("Item not found with id: ${rentalDto.carId}") }

        val customer = customerRepository.findById(rentalDto.customerId)
            .orElseThrow { ResourceNotFoundException("Item not found with id: ${rentalDto.customerId}") }

        val rental = Rental(
            startDate = rentalDto.startDate,
            endDate = rentalDto.endDate,
            customer = customer,
            car = car
        )

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