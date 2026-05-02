package com.coffeehaus.service;

import com.coffeehaus.entity.Roaster;
import com.coffeehaus.repository.RoasterRepository;
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
 * Unit tests for RoasterService.
 * Uses Mockito to isolate the service from the actual database.
 */
@ExtendWith(MockitoExtension.class)
class RoasterServiceTest {

    @Mock
    private RoasterRepository roasterRepo;

    @InjectMocks
    private RoasterService roasterService;

    private Roaster buildSampleRoaster() {
        Roaster r = new Roaster("Mock Roasters", "mock@roasters.com",
                                "Germany", "Dark", 2018);
        r.setRoasterId(1L);
        return r;
    }

    @Test
    @DisplayName("getAllRoasters delegates to repository findAll")
    void getAllRoasters_returnsList() {
        Roaster r1 = buildSampleRoaster();
        Roaster r2 = new Roaster("Second Roaster", "second@mail.com", "Spain", "Light", 2020);
        r2.setRoasterId(2L);

        when(roasterRepo.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Roaster> result = roasterService.getAllRoasters();

        assertEquals(2, result.size());
        verify(roasterRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("getRoasterById returns optional from repository")
    void getRoasterById_returnsOptional() {
        Roaster r = buildSampleRoaster();
        when(roasterRepo.findById(1L)).thenReturn(Optional.of(r));

        Optional<Roaster> found = roasterService.getRoasterById(1L);

        assertTrue(found.isPresent());
        assertEquals("Mock Roasters", found.get().getCompanyName());
    }

    @Test
    @DisplayName("createRoaster persists via repository save")
    void createRoaster_savesSuccessfully() {
        Roaster input = new Roaster("New Co.", "new@co.com", "France", "Medium", 2022);
        Roaster saved = new Roaster("New Co.", "new@co.com", "France", "Medium", 2022);
        saved.setRoasterId(5L);

        when(roasterRepo.save(any(Roaster.class))).thenReturn(saved);

        Roaster result = roasterService.createRoaster(input);

        assertNotNull(result.getRoasterId());
        assertEquals(5L, result.getRoasterId());
        verify(roasterRepo).save(input);
    }

    @Test
    @DisplayName("createRoaster throws RuntimeException on duplicate email")
    void createRoaster_throwsOnDuplicateEmail() {
        Roaster input = new Roaster("Dup Co.", "dup@mail.com", "UK", "Dark", 2021);
        when(roasterRepo.save(any())).thenThrow(new DataIntegrityViolationException("constraint violation"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roasterService.createRoaster(input));

        assertTrue(ex.getMessage().contains("dup@mail.com"),
                   "Error message should mention the conflicting email");
    }

    @Test
    @DisplayName("updateRoaster modifies fields and saves")
    void updateRoaster_updatesCorrectly() {
        Roaster existing = buildSampleRoaster();
        when(roasterRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(roasterRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Roaster updates = new Roaster("Updated Name", "updated@mail.com",
                                       "Italy", "Specialty", 2019);

        Roaster result = roasterService.updateRoaster(1L, updates);

        assertEquals("Updated Name", result.getCompanyName());
        assertEquals("updated@mail.com", result.getContactEmail());
        assertEquals("Italy", result.getOriginCountry());
    }

    @Test
    @DisplayName("updateRoaster throws when ID does not exist")
    void updateRoaster_throwsWhenNotFound() {
        when(roasterRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> roasterService.updateRoaster(99L, new Roaster()));
    }
}
