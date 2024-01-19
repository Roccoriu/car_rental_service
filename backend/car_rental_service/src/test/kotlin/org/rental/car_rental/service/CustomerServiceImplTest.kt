package org.rental.car_rental.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.customer.CustomerCreateMapper
import org.rental.car_rental.dto.customer.CustomerGetDto
import org.rental.car_rental.dto.customer.CustomerGetMapper
import org.rental.car_rental.model.Customer
import org.rental.car_rental.repository.CustomerRepository
import org.rental.car_rental.util.createCustomers
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class CustomerServiceImplTest {
    @MockBean
    private lateinit var customerRepository: CustomerRepository

    @MockBean
    private lateinit var customerCreateMapper: CustomerCreateMapper

    @MockBean
    private lateinit var customerGetMapper: CustomerGetMapper

    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        customerService = CustomerServiceImpl(
            customerRepository,
            customerCreateMapper,
            customerGetMapper
        )
    }

    @Test
    fun `should get all customers`() {
        val customer1 = Customer(
            1,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )

        val customer2 = Customer(
            2,
            "Jane",
            "Doe",
            LocalDate.of(1991, 1, 1),
            "jane.doe@example.com"
        )

        val customers = listOf(customer1, customer2)

        val customerGetDto1 =
            CustomerGetDto(
                1,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                emptyList()
            )

        val customerGetDto2 =
            CustomerGetDto(
                2,
                "Jane",
                "Doe",
                LocalDate.of(1991, 1, 1),
                "jane.doe@example.com",
                emptyList()
            )

        `when`(customerRepository.findAll()).thenReturn(customers)
        `when`(customerGetMapper.customerToDto(customer1)).thenReturn(customerGetDto1)
        `when`(customerGetMapper.customerToDto(customer2)).thenReturn(customerGetDto2)

        val result = customerService.getAllCustomers()

        verify(customerRepository).findAll()
        verify(customerGetMapper).customerToDto(customer1)
        verify(customerGetMapper).customerToDto(customer2)
        assert(result.equals(listOf(customerGetDto1, customerGetDto2)))
    }

    @Test
    fun `should get customer by id`() {
        val customerId = 1L
        val customer = Customer(customerId, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com")

        `when`(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))

        val result = customerService.getCustomerById(customerId)

        verify(customerRepository).findById(customerId)
        assert(result == customer)
    }

    @Test
    fun `should create customer`() {
        val customerDto = CustomerCreateDto(
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )
        val customer = Customer(
            0,
            "John",
            "Doe", LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )

        `when`(customerCreateMapper.dtoToCustomer(customerDto)).thenReturn(customer)
        `when`(customerRepository.save(customer)).thenReturn(customer.copy(id = 1))

        val result = customerService.createCustomer(customerDto)

        verify(customerCreateMapper).dtoToCustomer(customerDto)
        verify(customerRepository).save(customer)
        assert(result.id == 1L)
    }

    @Test
    fun `should update customer`() {
        val customerId = 1L
        val customerDto = CustomerCreateDto(
            "Jane",
            "Doe",
            LocalDate.of(1991, 1, 1),
            "jane.doe@example.com"
        )

        val customer = Customer(
            customerId,
            "Jane",
            "Doe",
            LocalDate.of(1991, 1, 1),
            "jane.doe@example.com"
        )

        `when`(customerRepository.findById(customerId)).thenReturn(Optional.of(customer))
        `when`(customerCreateMapper.dtoToCustomer(customerDto)).thenReturn(customer)
        `when`(customerRepository.save(customer)).thenReturn(customer)

        val result = customerService.updateCustomer(customerId, customerDto)

        verify(customerRepository).findById(customerId)
        verify(customerCreateMapper).dtoToCustomer(customerDto)
        verify(customerRepository).save(customer)
        assert(result.id == customerId)
    }

    @Test
    fun `should delete customer`() {
        val customerId = 1L

        doNothing().`when`(customerRepository).deleteById(customerId)

        customerService.deleteCustomer(customerId)

        verify(customerRepository).deleteById(customerId)
    }

}