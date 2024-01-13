package org.rental.car_rental.service

import java.util.*
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository

@Service
class CarService(private val carRepository: CarRepository) {
    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarsById(id: Long): Optional<Car> = carRepository.findById(id)
}