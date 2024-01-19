package org.rental.car_rental.util

import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.customer.CustomerCreateDto
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Customer
import org.rental.car_rental.model.Rental
import java.time.LocalDate


fun createTestCars(): List<Car> =
    listOf(
        Car(
            id = 1,
            category = "Sedan",
            brand = "Toyota",
            model = "Camry",
            year = 2022,
            color = "Blue",
            rentPriceDay = 50.0,
            automatic = true,
            seats = 5,
            image = "toyota_camry.jpg",
        ),
        Car(
            id = 2,
            category = "SUV",
            brand = "Honda",
            model = "CR-V",
            year = 2021,
            color = "Red",
            rentPriceDay = 60.0,
            automatic = true,
            seats = 5,
            image = "honda_cr-v.jpg",
        )
    )

fun createDtoTestCar(): CarCreateUpdateDto =
    CarCreateUpdateDto(
        category = "SUV",
        brand = "Honda",
        model = "CR-V",
        year = 2021,
        color = "Red",
        rentPriceDay = 60.0,
        automatic = true,
        seats = 5,
        imageData = "honda_cr-v.jpg",
    )


fun createCustomers(): List<Customer> =
    listOf(
        Customer(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            email = "john.doe@example.com"
        ),

        Customer(
            id = 2,
            firstName = "Jane",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 12, 31),
            email = "jane.smith@example.com"
        )

    )

fun createCustomerCreateDto(): CustomerCreateDto =
    CustomerCreateDto(
        firstName = "Jane",
        lastName = "Smith",
        dateOfBirth = LocalDate.of(1990, 12, 31),
        email = "jane.smith@example.com"
    )

fun createRentals(): List<Rental> =
    listOf(
        Rental(
            id = 1,
            startDate = LocalDate.of(2024, 1, 10),
            endDate = LocalDate.of(2024, 1, 15),
            customer = Customer(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = LocalDate.of(1990, 1, 1),
                email = "john.doe@example.com"
            ),
            car = Car(
                id = 1,
                category = "Sedan",
                brand = "Toyota",
                model = "Camry",
                year = 2022,
                color = "Blue",
                rentPriceDay = 50.0,
                automatic = true,
                seats = 5,
                image = "toyota_camry.jpg",
                rentals = mutableListOf()
            )
        ),
        Rental(
            id = 2,
            startDate = LocalDate.of(2024, 2, 5),
            endDate = LocalDate.of(2024, 2, 10),
            customer = Customer(
                id = 2,
                firstName = "Alice",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1992, 5, 20),
                email = "alice.smith@example.com"
            ),
            car = Car(
                id = 2,
                category = "SUV",
                brand = "Honda",
                model = "CR-V",
                year = 2021,
                color = "Red",
                rentPriceDay = 60.0,
                automatic = true,
                seats = 5,
                image = "honda_cr-v.jpg",
                rentals = mutableListOf()
            )
        )
    )

fun createRentalsDto(): RentalCreateDto =
    RentalCreateDto(
        startDate = LocalDate.of(2024, 2, 5),
        endDate = LocalDate.of(2024, 2, 10),
        carId = 1,
        customerId = 1
    )
