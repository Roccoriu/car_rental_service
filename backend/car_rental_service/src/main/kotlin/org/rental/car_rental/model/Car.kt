package org.rental.car_rental.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*;

@Entity
@Table(name = "car")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val category: String,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val rentPriceDay: Double,
    val isAutomatic: Boolean,
    val seats: Int,
    var image: String? = null,

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    val rentals: List<Rental>? = mutableListOf(),
)
