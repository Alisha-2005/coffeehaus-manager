package com.coffeehaus.repository;

import com.coffeehaus.entity.CoffeeBlend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access interface for CoffeeBlend entities.
 * Contains a custom JPQL query that performs an inner join with Roaster.
 */
@Repository
public interface CoffeeBlendRepository extends JpaRepository<CoffeeBlend, Long> {

    /**
     * Custom query: fetches every blend along with its roaster info
     * using an explicit INNER JOIN. This ensures only blends that
     * have a valid roaster reference are returned.
     */
    @Query("SELECT cb FROM CoffeeBlend cb INNER JOIN cb.roaster r ORDER BY r.companyName, cb.blendName")
    List<CoffeeBlend> fetchBlendsWithRoasterDetails();

    // Derived query: find blends by bean type
    List<CoffeeBlend> findByBeanType(String beanType);
}
