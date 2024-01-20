package org.rental.car_rental.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.rental.car_rental.model.Car
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Bool

interface CarRepository : JpaRepository<Car, Long> {
}

interface CarSearchRepository {
    fun filterCars(
        carType: List<String>?,
        brand: List<String>?,
        minPrice: Float?,
        maxPrice: Float?,
        minSeats: Int?,
        isAutomatic: Boolean?
    ): List<Car>
}

@Repository
@Primary
class CarSearchRepositoryImpl(private val entityManager: EntityManager) : CarSearchRepository {
    override fun filterCars(
        carType: List<String>?,
        brand: List<String>?,
        minPrice: Float?,
        maxPrice: Float?,
        minSeats: Int?,
        isAutomatic: Boolean?
    ): List<Car> {
        val cb = entityManager.criteriaBuilder
        val query = cb.createQuery(Car::class.java)
        val root = query.from(Car::class.java)

        val predicates = mutableListOf<Predicate>()

        carType?.let { predicates.add(root.get<String>("category").`in`(it)) }
        brand?.let { predicates.add(root.get<String>("brand").`in`(it)) }

        minPrice?.let { predicates.add(cb.ge(root.get<Double>("rentPriceDay"), it.toDouble())) }
        maxPrice?.let { predicates.add(cb.le(root.get<Double>("rentPriceDay"), it.toDouble())) }

        minSeats?.let { predicates.add(cb.ge(root.get<Int>("seats"), it)) }
        isAutomatic?.let { predicates.add(cb.equal(root.get<Boolean>("automatic"), it)) }

        query.select(root).where(*predicates.toTypedArray())

        return entityManager.createQuery(query).resultList
    }


}