package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.customer.CustomerGetDto
import org.rental.car_rental.model.Customer
import org.rental.car_rental.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/customers")
class CustomerController(private val customerService: CustomerService) {
    @GetMapping
    fun getCustomers(): List<CustomerGetDto> = customerService.getAllCustomers()

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Long): Customer = customerService.getCustomerById(id)

    @PostMapping
    fun postCustomer(@Valid @RequestBody customerDto: CustomerCreateDto): ResponseEntity<Customer> {
        val newCustomer = customerService.createCustomer(customerDto)
        return ResponseEntity(newCustomer, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putCustomer(
        @PathVariable id: Long,
        @Valid @RequestBody customerDto: CustomerCreateDto,
    ): ResponseEntity<Customer> {
        val newCustomer = customerService.updateCustomer(id, customerDto)
        return ResponseEntity(newCustomer, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedCustomer = customerService.deleteCustomer(id)
        return ResponseEntity(
            mapOf("message" to "successfully deleted", "resource" to deletedCustomer.toString()),
            HttpStatus.OK
        )
    }

}