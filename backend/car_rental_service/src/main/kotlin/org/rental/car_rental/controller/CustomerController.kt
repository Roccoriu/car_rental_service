package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.model.Customer
import org.rental.car_rental.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController(private val customerService: CustomerService) {
    @GetMapping
    @CrossOrigin
    fun getCustomers(): List<Customer> = customerService.getAllCustomers()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getCustomer(@PathVariable id: Long): Customer = customerService.getCustomerById(id)

    @PostMapping
    @CrossOrigin
    fun postCustomer(@Valid @RequestBody customerDto: CustomerCreateDto): ResponseEntity<Customer> {
        val newCustomer = customerService.createCustomer(customerDto)
        return ResponseEntity(newCustomer, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @CrossOrigin
    fun putCustomer(
            @PathVariable id: Long,
            @Valid @RequestBody customerDto: CustomerCreateDto,
    ): ResponseEntity<Customer> {
        val newCustomer = customerService.updateCustomer(id, customerDto)
        return ResponseEntity(newCustomer, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedCustomer = customerService.deleteCustomer(id)
        return ResponseEntity(
                mapOf("message" to "successfully deleted", "resource" to deletedCustomer.toString()),
                HttpStatus.OK
        )
    }

}