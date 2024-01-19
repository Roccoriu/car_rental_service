package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.mockito.Mockito.doNothing

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.dto.car.CarGetMapper
import org.rental.car_rental.service.CarService
import org.rental.car_rental.util.createDtoTestCar
import org.rental.car_rental.util.createTestCars
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@WebMvcTest(CarController::class)
class CarControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var carService: CarService


    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCars() {
        val carGetMapper = Mappers.getMapper(CarGetMapper::class.java)
        val testCars = createTestCars().map(carGetMapper::carToDto)

        `when`(carService.getAllCars()).thenReturn(testCars)

        mockMvc.perform(
            get("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun putCar() {
        val updatedCar = createTestCars()[0];
        val carDto = createDtoTestCar()
        val payload = objectMapper.writeValueAsString(carDto)

        `when`(carService.updateCar(1, carDto)).thenReturn(updatedCar)

        mockMvc.perform(
            put("/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)


    }

    @Test
    fun deleteCar() {
        doNothing().`when`(carService).deleteCar(1)

        mockMvc.perform(delete("/v1/cars/1"))
            .andExpect(status().isOk)

    }
}