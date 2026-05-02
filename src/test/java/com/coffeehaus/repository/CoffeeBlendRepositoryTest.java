package com.coffeehaus.repository;

import com.coffeehaus.entity.CoffeeBlend;
import com.coffeehaus.entity.Roaster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CoffeeBlendRepository, including the custom inner-join query.
 */
@DataJpaTest
class CoffeeBlendRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CoffeeBlendRepository blendRepository;

    private Roaster createAndPersistRoaster(String name, String email) {
        Roaster r = new Roaster(name, email, "TestCountry", "Medium", 2020);
        return entityManager.persist(r);
    }

    @Test
    @DisplayName("Saving a blend links it to its roaster correctly")
    void whenSaveBlend_thenRoasterLinkIsPreserved() {
        Roaster roaster = createAndPersistRoaster("LinkTest Roasters", "link@test.com");

        CoffeeBlend blend = new CoffeeBlend("TestBlend", "SKU-T01",
                "Citrus", 40.0, "Arabica", roaster);
        CoffeeBlend saved = blendRepository.save(blend);

        assertNotNull(saved.getBlendId());
        assertEquals(roaster.getRoasterId(), saved.getRoaster().getRoasterId(),
                     "Blend should reference the correct roaster");
    }

    @Test
    @DisplayName("Custom inner-join query returns blends with roaster info")
    void whenFetchBlendsWithRoasterDetails_thenReturnsJoinedData() {
        Roaster r1 = createAndPersistRoaster("JoinTest A", "joina@test.com");
        Roaster r2 = createAndPersistRoaster("JoinTest B", "joinb@test.com");

        entityManager.persist(new CoffeeBlend("BlendA", "SKU-J01", "Nutty", 35.0, "Arabica", r1));
        entityManager.persist(new CoffeeBlend("BlendB", "SKU-J02", "Fruity", 42.0, "Robusta", r2));
        entityManager.flush();

        List<CoffeeBlend> joinedResults = blendRepository.fetchBlendsWithRoasterDetails();

        assertEquals(2, joinedResults.size(), "Inner join should return 2 results");
        // Verify each blend has a non-null roaster (confirming the join worked)
        for (CoffeeBlend cb : joinedResults) {
            assertNotNull(cb.getRoaster(), "Each blend must have a roaster attached");
            assertNotNull(cb.getRoaster().getCompanyName());
        }
    }

    @Test
    @DisplayName("findByBeanType returns only matching blends")
    void whenFindByBeanType_thenFiltersCorrectly() {
        Roaster roaster = createAndPersistRoaster("BeanFilter Co.", "filter@test.com");

        entityManager.persist(new CoffeeBlend("ArabicaBlend", "SKU-F01", "Sweet", 30.0, "Arabica", roaster));
        entityManager.persist(new CoffeeBlend("RobustaBlend", "SKU-F02", "Bold",  28.0, "Robusta", roaster));
        entityManager.persist(new CoffeeBlend("ArabicaTwo",   "SKU-F03", "Mild",  33.0, "Arabica", roaster));
        entityManager.flush();

        List<CoffeeBlend> arabicaBlends = blendRepository.findByBeanType("Arabica");

        assertEquals(2, arabicaBlends.size(), "Should find exactly 2 Arabica blends");
    }
}
