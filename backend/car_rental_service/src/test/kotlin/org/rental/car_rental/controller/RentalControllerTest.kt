package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.mapstruct.factory.Mappers
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalGetMapper
import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import org.rental.car_rental.model.Rental
import org.rental.car_rental.service.RentalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(RentalController::class)
class RentalControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var rentalService: RentalService

    init {
        MockitoAnnotations.openMocks(this)
    }

    private fun createRentals(): List<Rental> =
        listOf(
            Rental(
                id = 1,
                startDate = LocalDate.of(2024, 1, 10),
                endDate = LocalDate.of(2024, 1, 15),
                customer = Customer(
                    id = 1,
                    firstName = "John",
                    lastName = "Doe",
                    dateOfBirth = LocalDate.of(1990, 1, 1),
                    email = "john.doe@example.com"
                ),
                car = Car(
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
                    rentals = mutableListOf()
                )
            ),
            Rental(
                id = 2,
                startDate = LocalDate.of(2024, 2, 5),
                endDate = LocalDate.of(2024, 2, 10),
                customer = Customer(
                    id = 2,
                    firstName = "Alice",
                    lastName = "Smith",
                    dateOfBirth = LocalDate.of(1992, 5, 20),
                    email = "alice.smith@example.com"
                ),
                car = Car(
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
                    rentals = mutableListOf()
                )
            )
        )

    private fun createRentalsDto(): RentalCreateDto =
        RentalCreateDto(
            startDate = LocalDate.of(2024, 2, 5),
            endDate = LocalDate.of(2024, 2, 10),
            carId = 1,
            customerId = 1
        )

    @Test
    fun getRentals() {
        val rentalGetMapper = Mappers.getMapper(RentalGetMapper::class.java)
        val testRentals = createRentals().map(rentalGetMapper::rentalToDto)

        `when`(rentalService.getAllRentals()).thenReturn(testRentals)

        mockMvc.perform(
            get("/v1/rental")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testRentals)))
    }

    @Test
    fun getRental() {
        val testRental = createRentals()[0]

        `when`(rentalService.getRentalsById(1)).thenReturn(testRental)

        mockMvc.perform(
            get("/v1/rental/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testRental)))
    }

    @Test
    fun postRental() {
        val newRental = createRentals()[0]
        val rentalDto = createRentalsDto()
        val payload = objectMapper.writeValueAsString(rentalDto)


        `when`(rentalService.createRental(rentalDto)).thenReturn(newRental)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/rental")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)

    }

    @Test
    fun putRental() {
        val updatedRental = createRentals()[0];
        val rentalDto = createRentalsDto()
        val payload = objectMapper.writeValueAsString(rentalDto)

        `when`(rentalService.updateRental(1, rentalDto)).thenReturn(updatedRental)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/rental/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)


    }

    @Test
    fun deleteRental() {
        doNothing().`when`(rentalService).deleteRental(1)

        mockMvc.perform(delete("/v1/rental/1"))
            .andExpect(status().isOk)
    }
}