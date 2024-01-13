package org.rental.car_rental.service

import org.rental.car_rental.dto.car.CarCreateDto
import org.rental.car_rental.dto.car.CarCreateMapper
import java.util.*
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository

@Service
class CarService(private val carRepository: CarRepository) {
    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarsById(id: Long): Optional<Car> = carRepository.findById(id)

    fun createCar(carDto: CarCreateDto): Car {
        val car = CarCreateMapper.INSTANCE.dtoToCar(carDto)
        return carRepository.save(car)
    }

    fun updateCar(carDto: CarCreateDto): Car {
        val car = CarCreateMapper.INSTANCE.dtoToCar(carDto)
        return carRepository.save(car)
    }

    fun deleteCar(id: Long) {
        val car = carRepository.deleteById(id)
        return car
    }
}