package org.rental.car_rental.service

import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetDto
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Car
import org.springframework.stereotype.Service
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.repository.CarSearchRepository
import org.rental.car_rental.utils.files.FileService
import org.springframework.context.annotation.Primary
import java.util.*

interface CarService {
    fun getAllCars(
        carType: List<String>?,
        brand: List<String>?,
        minPrice: Float?,
        maxPrice: Float?,
        minSeats: Int?,
        isAutomatic: Boolean?
    ): List<CarGetDto>

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
    private val carSearchRepository: CarSearchRepository,
    private val carCreateUpdateMapper: CarCreateUpdateMapper,
) : CarService {
    override fun getAllCars(
        carType: List<String>?,
        brand: List<String>?,
        minPrice: Float?,
        maxPrice: Float?,
        minSeats: Int?,
        isAutomatic: Boolean?
    ): List<CarGetDto> = carSearchRepository.filterCars(
        carType, brand, minPrice, maxPrice, minSeats, isAutomatic
    ).map(carGetMapper::carToDto)


    override fun getCarsById(id: Long): Car =
        carRepository.findById(id).orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    override fun createCar(carDto: CarCreateUpdateDto): Car {
        val car = carCreateUpdateMapper.dtoToCar(carDto)

        val imageData = carDto.imageData.split(",")
        val s3ObjectKey = handleImage(imageData[1], imageData[0])

        car.image = s3ObjectKey
        return carRepository.save(car)
    }

    override fun updateCar(id: Long, carDto: CarCreateUpdateDto): Car {
        val updatedCar = carCreateUpdateMapper.dtoToCar(carDto)
        val existing =
            carRepository.findById(id).orElseThrow { ResourceNotFoundException("Item not found with Id: $id") };

        if (existing.image != null) {
            fileService.deleteFile("joltx-car-rental", existing.image!!)
        }

        val imageData = carDto.imageData.split(",")
        val s3ObjectKey = handleImage(imageData[1], imageData[0])

        updatedCar.id = id
        updatedCar.image = s3ObjectKey

        return carRepository.save(updatedCar)
    }

    override fun deleteCar(id: Long) {
        val car = carRepository.findById(id).orElseThrow { ResourceNotFoundException("Item not found with id: $id") }

        if (car.image != null) {
            fileService.deleteFile("joltx-car-rental", car.image!!)
        }

        carRepository.deleteById(id)
    }

    private fun handleImage(imageData: String, dataType: String): String {
        val decodedImage = Base64.getDecoder().decode(imageData)
        val s3ObjectKey = fileService.generateObjectId(decodedImage)
        val mimeType = fileService.extractMimeType(dataType)

        fileService.uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)

        return s3ObjectKey
    }
}