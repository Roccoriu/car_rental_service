package org.rental.car_rental.service

import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetDto
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.utils.FileService
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
    private val fileService: FileService,
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
        val car = carCreateUpdateMapper.dtoToCar(carDto)
        val imageData = carDto.imageData.split(",")

        val decodedImage = Base64.getDecoder().decode(imageData[1])

        val s3ObjectKey = fileService.generateObjectId(decodedImage)
        val mimeType = fileService.extractMimeType(imageData[0])

        fileService.uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)

        car.image = s3ObjectKey
        return carRepository.save(car)
    }

    override fun updateCar(id: Long, carDto: CarCreateUpdateDto): Car {
        val updatedCar = carCreateUpdateMapper.dtoToCar(carDto)
        val existing = carRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") };

        if (existing.image != null) {
            fileService.deleteFile("joltx-car-rental", existing.image!!)
        }

        val imageData = carDto.imageData.split(",")
        val decodedImage = Base64.getDecoder().decode(imageData[1])
        val s3ObjectKey = fileService.generateObjectId(decodedImage)
        val mimeType = fileService.extractMimeType(imageData[0])

        fileService.uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)

        updatedCar.id = id
        updatedCar.image = s3ObjectKey

        return carRepository.save(updatedCar)
    }

    override fun deleteCar(id: Long) {
        val car = carRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

        if (car.image != null) {
            fileService.deleteFile("joltx-car-rental", car.image!!)
        }

        carRepository.deleteById(id)
    }
}