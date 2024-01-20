package org.rental.car_rental.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.model.Car
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.repository.CarSearchRepository
import org.rental.car_rental.util.createTestCars
import org.rental.car_rental.utils.files.FileService
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class CarServiceImplTest {
    @MockBean
    private lateinit var fileService: FileService

    @MockBean
    private lateinit var carGetMapper: CarGetMapper

    @MockBean
    private lateinit var carRepository: CarRepository

    @MockBean
    private lateinit var carSearchRepository: CarSearchRepository

    @MockBean
    private lateinit var carCreateUpdateMapper: CarCreateUpdateMapper

    private lateinit var carService: CarService

    @BeforeEach
    fun setUp() {
        carService = CarServiceImpl(
            fileService,
            carGetMapper,
            carRepository,
            carSearchRepository,
            carCreateUpdateMapper,
        )
    }

    @Test
    fun `should return list of cars`() {
        val cars = createTestCars()
        val carDtos = cars.map(carGetMapper::carToDto)

        `when`(
            carSearchRepository.filterCars(
                listOf(""), listOf(""), 0F, 0F, 0, false
            )
        ).thenReturn(cars)
        `when`(carGetMapper.carToDto(cars[0])).thenReturn(carDtos[0])

        val result = carService.getAllCars(listOf(""), listOf(""), 0F, 0F, 0, false)

        assertThat(result[0]).isEqualTo(carDtos[0])
        assertThat(result).hasSize(2)

    }

    @Test
    fun `should return single car`() {
        val car = createTestCars()[0]

        `when`(carRepository.findById(1L)).thenReturn(Optional.of(car))

        val result = carService.getCarsById(1)

        assertThat(result).isEqualTo(car)
    }

    @Test
    fun `should create car`() {
        val carDto = CarCreateUpdateDto(
            "SUV",
            "Toyota",
            "Model",
            2021,
            "Blue",
            50.0,
            true,
            5,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABYAAAAPUCAIAAACb"
        )

        val car = Car(
            0,
            "SUV",
            "Toyota",
            "Model",
            2021,
            "Blue",
            50.0,
            true,
            5,
            null
        )

        val s3ObjectKey = "s3ObjectKey"
        val mimeType = "image/png"
        val decodedImage = Base64.getDecoder().decode(carDto.imageData.split(",")[1])

        // Mocking behavior
        `when`(carCreateUpdateMapper.dtoToCar(carDto)).thenReturn(car)
        `when`(fileService.generateObjectId(decodedImage)).thenReturn(s3ObjectKey)
        `when`(
            fileService
                .extractMimeType("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABYAAAAPUCAIAAACb".split(",")[0])
        ).thenReturn(
            mimeType
        )

        doNothing().`when`(fileService).uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)
        `when`(carRepository.save(any(Car::class.java))).thenReturn(car.apply { this.image = s3ObjectKey })

        // Method execution
        val result = carService.createCar(carDto)

        // Assertions
        verify(carCreateUpdateMapper).dtoToCar(carDto)
        verify(fileService).generateObjectId(decodedImage)
        verify(fileService)
            .extractMimeType("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABYAAAAPUCAIAAACb".split(",")[0])

        verify(fileService).uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)
        verify(carRepository).save(any(Car::class.java))
        assert(result.image == s3ObjectKey)

    }

    @Test
    fun `should update car`() {
        val carDto = CarCreateUpdateDto(
            category = "SUV",
            brand = "Toyota",
            model = "Model",
            year = 2021,
            color = "Blue",
            rentPriceDay = 50.0,
            automatic = true,
            seats = 5,
            imageData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABYAAAAPUCAIAAACb"
        )

        // Create Car entity for repository response
        val existingCar = Car(
            id = 1,
            category = "SUV",
            brand = "Toyota",
            model = "Model",
            year = 2021,
            color = "Blue",
            rentPriceDay = 50.0,
            automatic = true,
            seats = 5,
            image = "existingImageKey"
        )

        val updatedCar = Car(
            id = existingCar.id,
            category = carDto.category,
            brand = carDto.brand,
            model = carDto.model,
            year = carDto.year,
            color = carDto.color,
            rentPriceDay = carDto.rentPriceDay,
            automatic = carDto.automatic,
            seats = carDto.seats,
            image = null // Image will be updated later
        )

        // Split image data to decode and extract MIME type
        val imageDataParts = carDto.imageData.split(",")
        val decodedImage = Base64.getDecoder().decode(imageDataParts[1])
        val s3ObjectKey = "newS3ObjectKey"
        val mimeType = "image/png"

        // Mocking behavior
        `when`(carCreateUpdateMapper.dtoToCar(carDto)).thenReturn(updatedCar)
        `when`(carRepository.findById(existingCar.id)).thenReturn(Optional.of(existingCar))
        `when`(fileService.generateObjectId(decodedImage)).thenReturn(s3ObjectKey)
        `when`(fileService.extractMimeType(imageDataParts[0])).thenReturn(mimeType)
        doNothing().`when`(fileService).uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)
        `when`(carRepository.save(any(Car::class.java))).thenReturn(updatedCar.apply { this.image = s3ObjectKey })

        // Execute the method under test
        val result = carService.updateCar(existingCar.id, carDto)

        // Assertions
        assert(result.id == existingCar.id)
        assert(result.image == s3ObjectKey)
        verify(carCreateUpdateMapper).dtoToCar(carDto)
        verify(carRepository).findById(existingCar.id)
        verify(fileService).generateObjectId(decodedImage)
        verify(fileService).extractMimeType(imageDataParts[0])
        verify(fileService).uploadFile("joltx-car-rental", s3ObjectKey, decodedImage, mimeType)
        verify(carRepository).save(any(Car::class.java))
    }

    @Test
    fun `should delete car`() {
        val carId = 1L

        val car = Car(
            id = carId,
            category = "SUV",
            brand = "Toyota",
            model = "Rav4",
            year = 2021,
            color = "Red",
            rentPriceDay = 50.0,
            automatic = true,
            seats = 5,
            image = "s3ObjectKey"
        )

        // Mocking the repository and file service behavior
        `when`(carRepository.findById(carId)).thenReturn(Optional.of(car))
        doNothing().`when`(fileService).deleteFile("joltx-car-rental", car.image!!)
        doNothing().`when`(carRepository).deleteById(carId)

        // Execute the method under test
        carService.deleteCar(carId)

        // Verify the interactions
        verify(carRepository).findById(carId)
        verify(fileService).deleteFile("joltx-car-rental", car.image!!)
        verify(carRepository).deleteById(carId)
    }
}