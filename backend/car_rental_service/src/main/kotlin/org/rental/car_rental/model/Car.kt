package org.rental.car_rental.model

data class Car(
    var id: Long,
    var brand: String,
    var model: String,
    var year: Int,
    var color: String,
    var rentPriceDay: Double,
    var isAutomatic: Boolean,
    var seats: Int,
    var image: String,
    var rentals: List<Rental> // Assuming Rental is another data class
)
