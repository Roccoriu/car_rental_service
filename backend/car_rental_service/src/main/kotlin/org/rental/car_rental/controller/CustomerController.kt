package org.rental.car_rental.controller

import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.model.Customer
import org.rental.car_rental.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/v1/customers")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping
    fun getCustomers(): List<Customer> = customerService.getAllCustomers()

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): Optional<Customer> = customerService.getCustomerById(id.toLong())

    @PostMapping
    fun postCustomer(@RequestBody customerDto: CustomerCreateDto): ResponseEntity<Customer> {
        val newCustomer = customerService.createCustomer(customerDto)
        return ResponseEntity(newCustomer, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putCustomer(
            @RequestBody customerDto: CustomerCreateDto,
            @PathVariable id: String
    ): ResponseEntity<Customer> {
        val newCustomer = customerService.updateCustomer(customerDto)
        return ResponseEntity(newCustomer, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: String): ResponseEntity<Map<String, String>> {
        val deletedCustomer = customerService.deleteCustomer(id.toLong())
        return ResponseEntity(
                mapOf("message" to "successfully deleted", "resource" to deletedCustomer.toString()),
                HttpStatus.OK
        )
    }

}