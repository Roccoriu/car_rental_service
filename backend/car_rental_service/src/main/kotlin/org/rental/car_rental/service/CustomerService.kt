package org.rental.car_rental.service

import org.rental.car_rental.model.Customer
import org.rental.car_rental.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    fun getAllCustomers(): List<Customer> = customerRepository.findAll()

    fun getCustomerById(id: Long): Optional<Customer> = customerRepository.findById(id)
}