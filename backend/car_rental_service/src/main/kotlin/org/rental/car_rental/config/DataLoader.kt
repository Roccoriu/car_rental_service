package org.rental.car_rental.config

import org.rental.car_rental.model.Car
import org.rental.car_rental.repository.CarRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class DataLoader(
    private val carRepository: CarRepository,
) : CommandLineRunner {
    val cars = listOf(
        Car(
            category = "SUV",
            brand = "Toyota",
            model = "Rav4",
            year = 2021,
            color = "Red",
            rentPriceDay = 50.0,
            seats = 5,
            image = "c1.png",
            isAutomatic = true
        ),
        Car(
            category = "Sedan",
            brand = "Honda",
            model = "Accord",
            year = 2019,
            color = "Blue",
            rentPriceDay = 40.0,
            seats = 4,
            image = "c2.png",
            isAutomatic = true
        ),
        Car(
            category = "Sports",
            brand = "Ferrari",
            model = "F8",
            year = 2020,
            color = "Yellow",
            rentPriceDay = 200.0,
            seats = 2,
            image = "c3.png",
            isAutomatic = true
        ),
        Car(
            category = "Hatchback",
            brand = "Ford",
            model = "Focus",
            year = 2018,
            color = "Green",
            rentPriceDay = 30.0,
            seats = 4,
            image = "c4.png",
            isAutomatic = false
        ),
        Car(
            category = "Convertible",
            brand = "BMW",
            model = "Z4",
            year = 2022,
            color = "Black",
            rentPriceDay = 70.0,
            seats = 2,
            image = "c5.png",
            isAutomatic = true
        ),
        Car(
            category = "Truck",
            brand = "Chevrolet",
            model = "Silverado",
            year = 2021,
            color = "White",
            rentPriceDay = 60.0,
            seats = 6,
            image = "c6.png",
            isAutomatic = true
        ),
        Car(
            category = "Minivan",
            brand = "Chrysler",
            model = "Pacifica",
            year = 2019,
            color = "Silver",
            rentPriceDay = 55.0,
            seats = 7,
            image = "c7.png",
            isAutomatic = true
        ),
        Car(
            category = "Coupe",
            brand = "Audi",
            model = "A5",
            year = 2020,
            color = "Grey",
            rentPriceDay = 65.0,
            seats = 4,
            image = "c8.png",
            isAutomatic = true
        ),
        Car(
            category = "Crossover",
            brand = "Hyundai",
            model = "Tucson",
            year = 2021,
            color = "Orange",
            rentPriceDay = 45.0,
            seats = 5,
            image = "c9.png",
            isAutomatic = true
        ),
        Car(
            category = "Luxury",
            brand = "Mercedes",
            model = "S-Class",
            year = 2022,
            color = "Purple",
            rentPriceDay = 100.0,
            seats = 4,
            image = "c10.png",
            isAutomatic = true
        ),
        Car(
            category = "SUV",
            brand = "Toyota",
            model = "Highlander",
            year = 2022,
            color = "Black",
            rentPriceDay = 60.0,
            seats = 5,
            image = "c11.png",
            isAutomatic = true
        ),
        Car(
            category = "Sedan",
            brand = "Toyota",
            model = "Corolla",
            year = 2020,
            color = "White",
            rentPriceDay = 45.0,
            seats = 4,
            image = "c12.png",
            isAutomatic = true
        ),
        Car(
            category = "Sports",
            brand = "Porsche",
            model = "911",
            year = 2021,
            color = "Red",
            rentPriceDay = 210.0,
            seats = 2,
            image = "c13.png",
            isAutomatic = true
        ),
        Car(
            category = "Hatchback",
            brand = "Volkswagen",
            model = "Golf",
            year = 2019,
            color = "Blue",
            rentPriceDay = 35.0,
            seats = 4,
            image = "c14.png",
            isAutomatic = false
        ),
        Car(
            category = "Convertible",
            brand = "Mercedes",
            model = "SLK",
            year = 2022,
            color = "Red",
            rentPriceDay = 80.0,
            seats = 2,
            image = "c15.png",
            isAutomatic = true
        ),
        Car(
            category = "Truck",
            brand = "Ford",
            model = "F-150",
            year = 2021,
            color = "Grey",
            rentPriceDay = 65.0,
            seats = 6,
            image = "c16.png",
            isAutomatic = true
        ),
        Car(
            category = "Minivan",
            brand = "Toyota",
            model = "Sienna",
            year = 2020,
            color = "Silver",
            rentPriceDay = 50.0,
            seats = 7,
            image = "c17.png",
            isAutomatic = true
        ),
        Car(
            category = "Coupe",
            brand = "BMW",
            model = "4 Series",
            year = 2021,
            color = "Blue",
            rentPriceDay = 75.0,
            seats = 4,
            image = "c18.png",
            isAutomatic = true
        ),
        Car(
            category = "Crossover",
            brand = "Nissan",
            model = "Rogue",
            year = 2022,
            color = "Black",
            rentPriceDay = 50.0,
            seats = 5,
            image = "c19.png",
            isAutomatic = true
        ),
        Car(
            category = "Luxury",
            brand = "Audi",
            model = "A8",
            year = 2021,
            color = "White",
            rentPriceDay = 110.0,
            seats = 4,
            image = "c20.png",
            isAutomatic = true
        )
    )

    override fun run(vararg args: String?) {
        carRepository.count().takeIf { it == 0L } ?: return
        cars.forEach { car -> carRepository.save(car) }
    }
}