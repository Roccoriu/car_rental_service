package org.rental.car_rental.service

import jakarta.persistence.EntityNotFoundException
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository

@Service
class CarService(private val carRepository: CarRepository) {
    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarsById(id: Long): Car = carRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    fun createCar(carDto: CarCreateUpdateDto): Car {
        val car = CarCreateUpdateMapper.INSTANCE.dtoToCar(carDto)
        return carRepository.save(car)
    }

    fun updateCar(id: Long, carDto: CarCreateUpdateDto): Car {
        carRepository
                .findById(id)
                .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") };

        val updatedCar = CarCreateUpdateMapper.INSTANCE.dtoToCar(carDto)

        updatedCar.id = id
        return carRepository.save(updatedCar)
    }

    fun deleteCar(id: Long) = carRepository.deleteById(id)
}