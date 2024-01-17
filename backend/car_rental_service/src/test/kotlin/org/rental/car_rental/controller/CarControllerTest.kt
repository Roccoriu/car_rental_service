package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CarController::class)
class CarControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carService: CarService

    @InjectMocks
    private lateinit var carController: CarController

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCars() {
        val testCars = listOf(
            Car(
                id = 1,
                category = "Sedan",
                brand = "Toyota",
                model = "Camry",
                year = 2022,
                color = "Blue",
                rentPriceDay = 50.0,
                isAutomatic = true,
                seats = 5,
                image = "toyota_camry.jpg",
                rentals = emptyList() // You can set rentals if needed
            ),
            Car(
                id = 2,
                category = "SUV",
                brand = "Honda",
                model = "CR-V",
                year = 2021,
                color = "Red",
                rentPriceDay = 60.0,
                isAutomatic = true,
                seats = 5,
                image = "honda_cr-v.jpg",
                rentals = emptyList() // You can set rentals if needed
            )
        )

        `when`(carService.getAllCars())
            .thenReturn(testCars)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testCars)))
    }

    @Test
    fun getCar() {
    }

    @Test
    fun postCar() {
    }

    @Test
    fun putCar() {
    }

    @Test
    fun deleteCar() {
    }
}