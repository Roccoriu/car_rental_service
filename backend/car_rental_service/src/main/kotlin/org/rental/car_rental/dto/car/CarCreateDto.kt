package org.rental.car_rental.dto.car

data class CarCreateDto(
        val id: Long,
        val category: String,
        val brand: String,
        val model: String,
        val year: Int,
        val color: String,
        val rentPriceDay: Double,
        val isAutomatic: Boolean,
        var seats: Int,
        var image: String

        //val rentals: List<Rental> = mutableListOf();
)