package org.rental.car_rental.model

import jakarta.persistence.*;

@Entity
@Table(name = "car")
class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0;

    val category: String = "";
    val brand: String = "";
    val model: String = "";
    val year: Int = 0;
    val color: String = "";
    val rentPriceDay: Double = 0.0;
    val isAutomatic: Boolean = false;
    val seats: Int = 0;
    val image: String = "";

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val rentals: List<Rental> = mutableListOf();
}
