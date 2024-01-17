package org.rental.car_rental.service

import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.customer.CustomerCreateMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Customer
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

interface CustomerService {
    fun getAllCustomers(): List<Customer>
    fun getCustomerById(id: Long): Customer
    fun createCustomer(customerDto: CustomerCreateDto): Customer
    fun updateCustomer(id: Long, customerDto: CustomerCreateDto): Customer
    fun deleteCustomer(id: Long)
}

@Service
@Primary
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {
    override fun getAllCustomers(): List<Customer> = customerRepository.findAll()

    override fun getCustomerById(id: Long): Customer = customerRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    override fun createCustomer(customerDto: CustomerCreateDto): Customer {
        val customer = Customer(
            firstName = customerDto.firstName,
            lastName = customerDto.lastName,
            email = customerDto.email,
            dateOfBirth = customerDto.dateOfBirth
        )
        return customerRepository.save(customer)
    }

    override fun updateCustomer(id: Long, customerDto: CustomerCreateDto): Customer {
        customerRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

        val customer = CustomerCreateMapper.INSTANCE.dtoToCustomer(customerDto)

        customer.id = id
        return customerRepository.save(customer)
    }

    override fun deleteCustomer(id: Long) = customerRepository.deleteById(id)
}