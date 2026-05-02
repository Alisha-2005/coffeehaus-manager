package com.coffeehaus.service;

import com.coffeehaus.entity.Roaster;
import com.coffeehaus.repository.RoasterRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business logic layer for Roaster operations.
 * Sits between the controller and repository to enforce rules.
 */
@Service
public class RoasterService {

    private final RoasterRepository roasterRepo;

    // Constructor-based injection (preferred over field injection)
    public RoasterService(RoasterRepository roasterRepo) {
        this.roasterRepo = roasterRepo;
    }

    /**
     * Retrieves every roaster record from the database.
     */
    public List<Roaster> getAllRoasters() {
        return roasterRepo.findAll();
    }

    /**
     * Looks up a single roaster by its primary key.
     * Returns empty Optional if not found.
     */
    public Optional<Roaster> getRoasterById(Long id) {
        return roasterRepo.findById(id);
    }

    /**
     * Persists a new roaster. Throws a runtime exception
     * if a unique constraint is violated (duplicate email).
     */
    public Roaster createRoaster(Roaster roaster) {
        try {
            return roasterRepo.save(roaster);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(
                "Could not save roaster — the email '" + roaster.getContactEmail()
                + "' may already exist in the system.", ex);
        }
    }

    /**
     * Updates an existing roaster's details.
     * Fetches the record first, applies changes, then saves.
     */
    public Roaster updateRoaster(Long id, Roaster updatedData) {
        Roaster existing = roasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Roaster with ID " + id + " was not found."));

        existing.setCompanyName(updatedData.getCompanyName());
        existing.setContactEmail(updatedData.getContactEmail());
        existing.setOriginCountry(updatedData.getOriginCountry());
        existing.setRoastStyle(updatedData.getRoastStyle());
        existing.setFoundedYear(updatedData.getFoundedYear());

        try {
            return roasterRepo.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(
                "Update failed — possibly a duplicate email conflict.", ex);
        }
    }
}
