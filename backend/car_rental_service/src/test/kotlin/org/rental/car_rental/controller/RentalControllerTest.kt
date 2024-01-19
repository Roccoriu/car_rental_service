package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.mapstruct.factory.Mappers
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.dto.rental.RentalGetMapper
import org.rental.car_rental.service.RentalService
import org.rental.car_rental.util.createRentals
import org.rental.car_rental.util.createRentalsDto
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