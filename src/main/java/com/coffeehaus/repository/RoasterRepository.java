package com.coffeehaus.repository;

import com.coffeehaus.entity.Roaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access interface for Roaster entities.
 * Spring Data JPA auto-generates the implementation at runtime.
 */
@Repository
public interface RoasterRepository extends JpaRepository<Roaster, Long> {

    // Derived query: look up roaster by company name
    Roaster findByCompanyName(String companyName);
}
