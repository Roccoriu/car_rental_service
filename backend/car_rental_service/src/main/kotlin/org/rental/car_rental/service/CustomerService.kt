package org.rental.car_rental.service

import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.customer.CustomerCreateMapper
import org.rental.car_rental.model.Customer
import org.rental.car_rental.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    fun getAllCustomers(): List<Customer> = customerRepository.findAll()

    fun getCustomerById(id: Long): Optional<Customer> = customerRepository.findById(id)

    fun createCustomer(customerDto: CustomerCreateDto): Customer {
        val customer = CustomerCreateMapper.INSTANCE.dtoToCustomer(customerDto)
        return customerRepository.save(customer)
    }

    fun updateCustomer(customerDto: CustomerCreateDto): Customer {
        val customer = CustomerCreateMapper.INSTANCE.dtoToCustomer(customerDto)
        return customerRepository.save(customer)
    }

    fun deleteCustomer(id: Long) = customerRepository.deleteById(id)
}