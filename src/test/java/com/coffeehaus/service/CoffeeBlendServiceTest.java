package com.coffeehaus.service;

import com.coffeehaus.entity.CoffeeBlend;
import com.coffeehaus.entity.Roaster;
import com.coffeehaus.repository.CoffeeBlendRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CoffeeBlendService.
 * Repository interactions are mocked so we only test business logic.
 */
@ExtendWith(MockitoExtension.class)
class CoffeeBlendServiceTest {

    @Mock
    private CoffeeBlendRepository blendRepo;

    @InjectMocks
    private CoffeeBlendService blendService;

    private Roaster sampleRoaster() {
        Roaster r = new Roaster("SampleRoast", "sample@roast.com", "Mexico", "Medium", 2017);
        r.setRoasterId(10L);
        return r;
    }

    private CoffeeBlend sampleBlend(Roaster roaster) {
        CoffeeBlend b = new CoffeeBlend("MockBlend", "SKU-M01",
                "Caramel & Vanilla", 45.0, "Arabica", roaster);
        b.setBlendId(100L);
        return b;
    }

    @Test
    @DisplayName("getAllBlends returns list from repository")
    void getAllBlends_returnsList() {
        Roaster r = sampleRoaster();
        when(blendRepo.findAll()).thenReturn(Arrays.asList(
                sampleBlend(r),
                new CoffeeBlend("Blend2", "SKU-M02", "Floral", 50.0, "Liberica", r)
        ));

        List<CoffeeBlend> result = blendService.getAllBlends();

        assertEquals(2, result.size());
        verify(blendRepo).findAll();
    }

    @Test
    @DisplayName("getBlendById returns the correct blend")
    void getBlendById_returnsBlend() {
        CoffeeBlend blend = sampleBlend(sampleRoaster());
        when(blendRepo.findById(100L)).thenReturn(Optional.of(blend));

        Optional<CoffeeBlend> found = blendService.getBlendById(100L);

        assertTrue(found.isPresent());
        assertEquals("MockBlend", found.get().getBlendName());
    }

    @Test
    @DisplayName("getBlendsWithRoasterInfo calls the custom join query")
    void getBlendsWithRoasterInfo_delegatesToCustomQuery() {
        Roaster r = sampleRoaster();
        CoffeeBlend b = sampleBlend(r);
        when(blendRepo.fetchBlendsWithRoasterDetails()).thenReturn(List.of(b));

        List<CoffeeBlend> joinedData = blendService.getBlendsWithRoasterInfo();

        assertEquals(1, joinedData.size());
        assertEquals("SampleRoast", joinedData.get(0).getRoaster().getCompanyName());
        verify(blendRepo).fetchBlendsWithRoasterDetails();
    }

    @Test
    @DisplayName("createBlend saves successfully")
    void createBlend_savesOk() {
        CoffeeBlend input = new CoffeeBlend("NewBlend", "SKU-N01",
                "Honey", 38.0, "Robusta", sampleRoaster());
        CoffeeBlend saved = new CoffeeBlend("NewBlend", "SKU-N01",
                "Honey", 38.0, "Robusta", sampleRoaster());
        saved.setBlendId(200L);

        when(blendRepo.save(any())).thenReturn(saved);

        CoffeeBlend result = blendService.createBlend(input);

        assertEquals(200L, result.getBlendId());
    }

    @Test
    @DisplayName("createBlend throws on duplicate SKU code")
    void createBlend_throwsOnDuplicateSku() {
        CoffeeBlend input = new CoffeeBlend("DupBlend", "SKU-DUP",
                "Bold", 30.0, "Arabica", sampleRoaster());
        when(blendRepo.save(any())).thenThrow(new DataIntegrityViolationException("sku conflict"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> blendService.createBlend(input));

        assertTrue(ex.getMessage().contains("SKU-DUP"));
    }

    @Test
    @DisplayName("updateBlend modifies and persists the blend")
    void updateBlend_updatesCorrectly() {
        CoffeeBlend existing = sampleBlend(sampleRoaster());
        when(blendRepo.findById(100L)).thenReturn(Optional.of(existing));
        when(blendRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Roaster newRoaster = new Roaster("OtherRoast", "other@mail.com",
                "Chile", "Light", 2021);
        newRoaster.setRoasterId(20L);
        CoffeeBlend updates = new CoffeeBlend("UpdatedBlend", "SKU-U01",
                "Cherry", 55.0, "Excelsa", newRoaster);

        CoffeeBlend result = blendService.updateBlend(100L, updates);

        assertEquals("UpdatedBlend", result.getBlendName());
        assertEquals("SKU-U01", result.getSkuCode());
        assertEquals(20L, result.getRoaster().getRoasterId());
    }

    @Test
    @DisplayName("updateBlend throws when blend ID does not exist")
    void updateBlend_throwsWhenNotFound() {
        when(blendRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> blendService.updateBlend(999L, new CoffeeBlend()));
    }
}
