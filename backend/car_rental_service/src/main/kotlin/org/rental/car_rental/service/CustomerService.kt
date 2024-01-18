package org.rental.car_rental.service

import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.customer.CustomerCreateMapper
import org.rental.car_rental.dto.customer.CustomerGetDto
import org.rental.car_rental.dto.customer.CustomerGetMapper
import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.rental.car_rental.model.Customer
import org.rental.car_rental.repository.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

interface CustomerService {
    fun getAllCustomers(): List<CustomerGetDto>
    fun getCustomerById(id: Long): Customer
    fun createCustomer(customerDto: CustomerCreateDto): Customer
    fun updateCustomer(id: Long, customerDto: CustomerCreateDto): Customer
    fun deleteCustomer(id: Long)
}

@Service
@Primary
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val customerCreateMapper: CustomerCreateMapper,
    private val customerGetMapper: CustomerGetMapper,
) : CustomerService {
    override fun getAllCustomers(): List<CustomerGetDto> =
        customerRepository.findAll().map(customerGetMapper::customerToDto)

    override fun getCustomerById(id: Long): Customer = customerRepository
        .findById(id)
        .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

    override fun createCustomer(customerDto: CustomerCreateDto): Customer {
        val customer = customerCreateMapper.dtoToCustomer(customerDto)
        return customerRepository.save(customer)
    }

    override fun updateCustomer(id: Long, customerDto: CustomerCreateDto): Customer {
        customerRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Item not found with Id: $id") }

        val customer = customerCreateMapper.dtoToCustomer(customerDto)

        customer.id = id
        return customerRepository.save(customer)
    }

    override fun deleteCustomer(id: Long) = customerRepository.deleteById(id)
}