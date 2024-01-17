package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@WebMvcTest(CarController::class)
class CarControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carService: CarService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        MockitoAnnotations.openMocks(this)
    }

    private fun createTestCars(): List<Car> =
        listOf(
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
            )
        )

    private fun createDtoTestCar(): CarCreateUpdateDto =
        CarCreateUpdateDto(
            category = "SUV",
            brand = "Honda",
            model = "CR-V",
            year = 2021,
            color = "Red",
            rentPriceDay = 60.0,
            isAutomatic = true,
            seats = 5,
            image = "honda_cr-v.jpg",
        )

    @Test
    fun getCars() {
        val testCars = createTestCars()

        `when`(carService.getAllCars()).thenReturn(testCars)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/v1/cars")
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testCars)))
    }

    @Test
    fun getCar() {
        val testCars = createTestCars();
        `when`(carService.getCarsById(1)).thenReturn(testCars[1])

        mockMvc.perform(
            get("/v1/cars/1")
                .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testCars[1])))

    }

    @Test
    fun postCar() {
        val newCar = createTestCars()[0]
        val carDto = createDtoTestCar()
        val payload = objectMapper.writeValueAsString(carDto)

        `when`(carService.createCar(carDto)).thenReturn(newCar)

        mockMvc.perform(
            post("/v1/cars")
                .contentType(APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)
        //.andExpect(content().json(objectMapper.writeValueAsString(newCar)))
    }

    @Test
    fun putCar() {
        val updatedCar = createTestCars()[0];
        val carDto = createDtoTestCar()
        val payload = objectMapper.writeValueAsString(carDto)

        `when`(carService.updateCar(1, carDto)).thenReturn(updatedCar)

        mockMvc.perform(
            put("/v1/cars/1")
                .contentType(APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)
        //    .andExpect(content().json(jsonRequest))


    }

    @Test
    fun deleteCar() {
        doNothing().`when`(carService).deleteCar(1)

        mockMvc.perform(delete("/v1/cars/1"))
            .andExpect(status().isOk)

    }
}