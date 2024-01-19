package org.rental.car_rental.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.mapstruct.factory.Mappers
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.rental.car_rental.dto.customer.CustomerGetMapper
import org.rental.car_rental.service.CustomerService
import org.rental.car_rental.util.createCustomerCreateDto
import org.rental.car_rental.util.createCustomers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CustomerController::class)
class CustomerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var customerService: CustomerService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test

    fun getCustomers() {
        val customerGetMapper = Mappers.getMapper(CustomerGetMapper::class.java)
        val testCustomers = createCustomers().map(customerGetMapper::customerToDto)

        `when`(customerService.getAllCustomers()).thenReturn(testCustomers)

        mockMvc.perform(
            get("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testCustomers)))

    }

    @Test
    fun getCustomer() {
        val testCustomer = createCustomers()[0]

        `when`(customerService.getCustomerById(1)).thenReturn(testCustomer)

        mockMvc.perform(
            get("/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(testCustomer)))
    }

    @Test
    fun postCustomer() {
        val newCustomer = createCustomers()[0]
        val customerDto = createCustomerCreateDto()
        val payload = objectMapper.writeValueAsString(customerDto)

        `when`(customerService.createCustomer(customerDto)).thenReturn(newCustomer)

        mockMvc.perform(
            post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun putCustomer() {
        val updatedCustomer = createCustomers()[0]
        val customerDto = createCustomerCreateDto()
        val payload = objectMapper.writeValueAsString(customerDto)

        `when`(customerService.updateCustomer(1, customerDto)).thenReturn(updatedCustomer)

        mockMvc.perform(
            put("/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(status().isOk)

    }

    @Test
    fun deleteCustomer() {
        doNothing().`when`(customerService).deleteCustomer(1)

        mockMvc.perform(delete("/v1/customers/1"))
            .andExpect(status().isOk)
    }
}