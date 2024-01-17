package org.rental.car_rental.service

import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalGetDto
import org.rental.car_rental.dto.rental.RentalGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.repository.CustomerRepository
import org.rental.car_rental.repository.RentalRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service


interface RentalService {
    fun getAllRentals(): List<RentalGetDto>
    fun getRentalsById(id: Long): Rental
    fun createRental(rentalDto: RentalCreateDto): Rental
    fun updateRental(id: Long, rentalDto: RentalCreateDto): Rental
    fun deleteRental(id: Long)
}

@Service
@Primary
class RentalServiceImpl(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository,
    private val rentalGetMapper: RentalGetMapper,
) : RentalService {
    override fun getAllRentals(): List<RentalGetDto> =
        rentalRepository.findAll().map(rentalGetMapper::rentalToDto)

    override fun getRentalsById(id: Long): Rental = rentalRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

    override fun createRental(rentalDto: RentalCreateDto): Rental {
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

    override fun updateRental(id: Long, rentalDto: RentalCreateDto): Rental {
        val rental = rentalRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

        val car = carRepository.findById(rentalDto.carId)
            .orElseThrow { ResourceNotFoundException("Item not found with id: ${rentalDto.carId}") }

        val customer = customerRepository.findById(rentalDto.customerId)
            .orElseThrow { ResourceNotFoundException("Item not found with id: ${rentalDto.customerId}") }

        rental.car = car
        rental.customer = customer
        rental.startDate = rentalDto.startDate
        rental.endDate = rentalDto.endDate

        return rentalRepository.save(rental)
    }

    override fun deleteRental(id: Long) = rentalRepository.deleteById(id)
}

