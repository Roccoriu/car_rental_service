package org.rental.car_rental.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.rental.car_rental.dto.car.CarCreateUpdateMapper
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.utils.files.FileService
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class CarServiceImplTest {
    @MockBean
    private lateinit var fileService: FileService

    @MockBean
    private lateinit var carGetMapper: CarGetMapper

    @MockBean
    private lateinit var carRepository: CarRepository

    @MockBean
    private lateinit var carCreateUpdateMapper: CarCreateUpdateMapper

    @Test
    fun `should return list of cars`() {

    }

    @Test
    fun getCarsById() {
    }

    @Test
    fun createCar() {
    }

    @Test
    fun updateCar() {
    }

    @Test
    fun deleteCar() {
    }
}