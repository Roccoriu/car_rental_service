package org.rental.car_rental.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalGetDto
import org.rental.car_rental.dto.rental.RentalGetMapper
import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import org.rental.car_rental.model.Rental
import org.rental.car_rental.repository.CarRepository
import org.rental.car_rental.repository.CustomerRepository
import org.rental.car_rental.repository.RentalRepository
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class RentalServiceImplTest {
    @MockBean
    private lateinit var rentalRepository: RentalRepository

    @MockBean
    private lateinit var carRepository: CarRepository

    @MockBean
    private lateinit var customerRepository: CustomerRepository

    @MockBean
    private lateinit var rentalGetMapper: RentalGetMapper

    private lateinit var rentalService: RentalService

    @BeforeEach
    fun setUp() {
        rentalService = RentalServiceImpl(
            rentalRepository,
            carRepository,
            customerRepository,
            rentalGetMapper
        )
    }

    @Test
    fun `should get all rentals`() {
        val customer = Customer(
            1,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )

        val car = Car(
            0,
            "SUV",
            "Toyota",
            "Model",
            2021,
            "Blue",
            50.0,
            true,
            5,
            null
        )

        val rental1 = Rental(
            id = 1,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            customer = customer,
            car = car
        )

        val rental2 = Rental(
            id = 2,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(2),
            customer = customer,
            car = car
        )

        `when`(rentalRepository.findAll()).thenReturn(listOf(rental1, rental2))
        `when`(rentalGetMapper.rentalToDto(rental1)).thenReturn(
            RentalGetDto(
                rental1.startDate,
                rental1.endDate,
                rental1.customer ?: customer,
                rental1.car ?: car
            )
        )
        `when`(rentalGetMapper.rentalToDto(rental2)).thenReturn(
            RentalGetDto(
                rental2.startDate,
                rental2.endDate,
                rental2.customer ?: customer,
                rental2.car ?: car           // Handle null values
            )
        )

        val result = rentalService.getAllRentals()

        verify(rentalRepository).findAll()
        verify(rentalGetMapper).rentalToDto(rental1)
        verify(rentalGetMapper).rentalToDto(rental2)
        assertThat(result).hasSize(2)
    }


    @Test
    fun `should get rental by id`() {
        val rentalId = 1L
        val rental = Rental(id = rentalId, startDate = LocalDate.now(), endDate = LocalDate.now().plusDays(1))

        `when`(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental))

        val result = rentalService.getRentalsById(rentalId)

        verify(rentalRepository).findById(rentalId)
        assertThat(result).isEqualTo(rental)
    }


    @Test
    fun `should create rental`() {
        val rentalDto = RentalCreateDto(
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            customerId = 1L,
            carId = 2L
        )

        val customer = Customer(
            1,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )

        val car = Car(
            0,
            "SUV",
            "Toyota",
            "Model",
            2021,
            "Blue",
            50.0,
            true,
            5,
            null
        )


        val rental =
            Rental(startDate = rentalDto.startDate, endDate = rentalDto.endDate, customer = customer, car = car)

        `when`(customerRepository.findById(rentalDto.customerId)).thenReturn(Optional.of(customer))
        `when`(carRepository.findById(rentalDto.carId)).thenReturn(Optional.of(car))
        `when`(rentalRepository.save(any(Rental::class.java))).thenReturn(rental)

        val result = rentalService.createRental(rentalDto)

        verify(customerRepository).findById(rentalDto.customerId)
        verify(carRepository).findById(rentalDto.carId)
        verify(rentalRepository).save(any(Rental::class.java))
        assertThat(result.startDate).isEqualTo(rentalDto.startDate)
        assertThat(result.endDate).isEqualTo(rentalDto.endDate)
    }

    @Test
    fun `should update rental`() {
        val rentalId = 1L
        val rentalDto = RentalCreateDto(
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(1),
            customerId = 2L,
            carId = 3L
        )
        val existingRental = Rental(id = rentalId, startDate = LocalDate.now().minusDays(1), endDate = LocalDate.now())
        val customer = Customer(
            1,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "john.doe@example.com"
        )

        val car = Car(
            0,
            "SUV",
            "Toyota",
            "Model",
            2021,
            "Blue",
            50.0,
            true,
            5,
            null
        )

        `when`(rentalRepository.findById(rentalId)).thenReturn(Optional.of(existingRental))
        `when`(customerRepository.findById(rentalDto.customerId)).thenReturn(Optional.of(customer))
        `when`(carRepository.findById(rentalDto.carId)).thenReturn(Optional.of(car))
        `when`(rentalRepository.save(any(Rental::class.java))).thenReturn(existingRental.apply {
            this.startDate = rentalDto.startDate
            this.endDate = rentalDto.endDate
            this.customer = customer
            this.car = car
        })

        val result = rentalService.updateRental(rentalId, rentalDto)

        verify(rentalRepository).findById(rentalId)
        verify(customerRepository).findById(rentalDto.customerId)
        verify(carRepository).findById(rentalDto.carId)
        verify(rentalRepository).save(any(Rental::class.java))
        assertThat(result.startDate).isEqualTo(rentalDto.startDate)
        assertThat(result.endDate).isEqualTo(rentalDto.endDate)
    }

    @Test
    fun `should delete rental`() {
        val rentalId = 1L

        doNothing().`when`(rentalRepository).deleteById(rentalId)

        rentalService.deleteRental(rentalId)

        verify(rentalRepository).deleteById(rentalId)
    }
}