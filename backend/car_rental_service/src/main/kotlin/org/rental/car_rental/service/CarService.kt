package org.rental.car_rental.service

import org.rental.car_rental.model.Car
import org.rental.car_rental.model.Rental
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CarService {

    fun getCars(): List<Car> {
        val rentalsForCar1 = listOf(
            Rental(1, 1, "Alice", LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15)),
            Rental(2, 1, "Bob", LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 25))
        )

        val rentalsForCar2 = listOf(
            Rental(3, 2, "Jenny", LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 15))
        )

        // Initializing cars with sample data
        val car1 = Car(
            id = 1,
            brand = "Toyota",
            model = "Camry",
            year = 2022,
            color = "Red",
            rentPriceDay = 100.0,
            isAutomatic = true,
            seats = 5,
            image = "image_url_1",
            rentals = rentalsForCar1
        )

        val car2 = Car(
            id = 2,
            brand = "Honda",
            model = "Civic",
            year = 2021,
            color = "Blue",
            rentPriceDay = 90.0,
            isAutomatic = false,
            seats = 5,
            image = "image_url_2",
            rentals = rentalsForCar2
        )

        return listOf(car1, car2)
    }
}