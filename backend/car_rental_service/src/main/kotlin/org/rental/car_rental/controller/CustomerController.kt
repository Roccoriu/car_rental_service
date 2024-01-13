package org.rental.car_rental.controller

import org.rental.car_rental.model.Customer
import org.rental.car_rental.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
class CustomerController(private val customerService: CustomerService) {

    @GetMapping("/customers")
    fun getCustomers(): List<Customer> = customerService.getAllCustomers()

    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable id: String): Optional<Customer> = customerService.getCustomerById(id.toLong())

}