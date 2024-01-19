package org.rental.car_rental.service

import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetDto
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.utils.S3Service
import org.springframework.context.annotation.Primary
import java.util.*
import java.util.regex.Pattern

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
    private val s3Service: S3Service,
    private val carGetMapper: CarGetMapper,
    private val carRepository: CarRepository,
    private val carCreateUpdateMapper: CarCreateUpdateMapper,
) : CarService {
    override fun getAllCars(): List<CarGetDto> =
        carRepository.findAll().map(carGetMapper::carToDto)

    override fun getCarsById(id: Long): Car = carRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    override fun createCar(carDto: CarCreateUpdateDto): Car {
        val mimeTypePattern = "data:([a-zA-Z]+/[a-zA-Z]+);base64".toRegex()

        val car = carCreateUpdateMapper.dtoToCar(carDto)
        val imageDataParts = carDto.imageData.split(",")

        val imageData = Base64.getDecoder().decode(imageDataParts[1])
        val imageKey = s3Service.generateObjectId(imageData)

        val mimeType = imageDataParts.getOrNull(0)
            ?.let {
                mimeTypePattern
                    .find(it)
                    ?.groupValues
                    ?.getOrNull(1)
            } ?: "image/jpg"

        s3Service.uploadToS3("joltx-car-rental", imageKey, imageData, mimeType)

        car.image = imageKey
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

    override fun deleteCar(id: Long) {
        val car = carRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

        if (car.image != null) {
            s3Service.deleteFromS3("joltx-car-rental", car.image!!)
        }

        carRepository.deleteById(id)
    }
}