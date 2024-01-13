package org.rental.car_rental.dto.car

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.rental.car_rental.model.Car

data class CarCreateDto(
        //val id: Long,
        val category: String,
        val brand: String,
        val model: String,
        val year: Int,
        val color: String,
        val rentPriceDay: Double,
        val isAutomatic: Boolean,
        val seats: Int,
        val image: String

        //val rentals: List<Rental> = mutableListOf();
)

@Mapper
interface CarCreateMapper {
    fun carToDto(car: Car): CarCreateDto
    fun dtoToCar(car: CarCreateDto): Car

    companion object {
        val INSTANCE: CarCreateMapper = Mappers.getMapper(CarCreateMapper::class.java)
    }
}