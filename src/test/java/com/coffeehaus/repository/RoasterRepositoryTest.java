package com.coffeehaus.repository;

import com.coffeehaus.entity.Roaster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the RoasterRepository.
 * Uses @DataJpaTest which configures an in-memory DB and rolls back after each test.
 */
@DataJpaTest
class RoasterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoasterRepository roasterRepository;

    @Test
    @DisplayName("Saving a roaster persists it and assigns an ID")
    void whenSaveRoaster_thenItIsPersisted() {
        Roaster roaster = new Roaster("TestRoasters Inc.", "test@roasters.com",
                                       "Canada", "Medium", 2020);

        Roaster saved = roasterRepository.save(roaster);

        assertNotNull(saved.getRoasterId(), "Saved roaster should have a generated ID");
        assertEquals("TestRoasters Inc.", saved.getCompanyName());
    }

    @Test
    @DisplayName("findAll returns all persisted roasters")
    void whenFindAll_thenReturnsAllRoasters() {
        entityManager.persist(new Roaster("Alpha Roast", "alpha@mail.com", "Kenya", "Dark", 2018));
        entityManager.persist(new Roaster("Beta Roast",  "beta@mail.com",  "Peru",  "Light", 2019));
        entityManager.flush();

        List<Roaster> results = roasterRepository.findAll();

        assertEquals(2, results.size(), "Should retrieve exactly 2 roasters");
    }

    @Test
    @DisplayName("findById returns the correct roaster")
    void whenFindById_thenReturnsCorrectRoaster() {
        Roaster persisted = entityManager.persist(
            new Roaster("Gamma Coffee", "gamma@mail.com", "Brazil", "Specialty", 2015));
        entityManager.flush();

        Optional<Roaster> found = roasterRepository.findById(persisted.getRoasterId());

        assertTrue(found.isPresent(), "Roaster should be found by ID");
        assertEquals("Gamma Coffee", found.get().getCompanyName());
    }

    @Test
    @DisplayName("findByCompanyName returns matching roaster")
    void whenFindByCompanyName_thenReturnsMatch() {
        entityManager.persist(new Roaster("Unique Beans", "unique@mail.com",
                                           "Japan", "Light", 2021));
        entityManager.flush();

        Roaster found = roasterRepository.findByCompanyName("Unique Beans");

        assertNotNull(found, "Should find roaster by company name");
        assertEquals("unique@mail.com", found.getContactEmail());
    }

    @Test
    @DisplayName("findById returns empty for non-existent ID")
    void whenFindByInvalidId_thenReturnsEmpty() {
        Optional<Roaster> result = roasterRepository.findById(9999L);
        assertTrue(result.isEmpty(), "Should return empty for non-existent ID");
    }
}
