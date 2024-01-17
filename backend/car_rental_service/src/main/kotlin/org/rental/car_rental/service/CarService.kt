package org.rental.car_rental.service

import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetDto
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository
import org.springframework.context.annotation.Primary

interface CarService {
    fun getAllCars(): List<CarGetDto>
    fun getCarsById(id: Long): Car
    fun createCar(carDto: CarCreateUpdateDto): Car
    fun updateCar(id: Long, carDto: CarCreateUpdateDto): Car
    fun deleteCar(id: Long)
}

@Service
@Primary
class CarServiceImpl(
    private val carRepository: CarRepository,
    private val carGetMapper: CarGetMapper,
    private val carCreateUpdateMapper: CarCreateUpdateMapper,
) : CarService {
    override fun getAllCars(): List<CarGetDto> =
        carRepository.findAll().map(carGetMapper::carToDto)

    override fun getCarsById(id: Long): Car = carRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    override fun createCar(carDto: CarCreateUpdateDto): Car {
        val car = carCreateUpdateMapper.dtoToCar(carDto)
        return carRepository.save(car)
    }

    override fun updateCar(id: Long, carDto: CarCreateUpdateDto): Car {
        carRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") };

        val updatedCar = carCreateUpdateMapper.dtoToCar(carDto)

        updatedCar.id = id
        return carRepository.save(updatedCar)
    }

    override fun deleteCar(id: Long) = carRepository.deleteById(id)
}