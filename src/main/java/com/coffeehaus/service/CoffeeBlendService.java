package com.coffeehaus.service;

import com.coffeehaus.entity.CoffeeBlend;
import com.coffeehaus.repository.CoffeeBlendRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Handles the business rules for CoffeeBlend operations.
 * Delegates persistence calls to CoffeeBlendRepository.
 */
@Service
public class CoffeeBlendService {

    private final CoffeeBlendRepository blendRepo;

    public CoffeeBlendService(CoffeeBlendRepository blendRepo) {
        this.blendRepo = blendRepo;
    }

    /** Fetch all blends stored in the database. */
    public List<CoffeeBlend> getAllBlends() {
        return blendRepo.findAll();
    }

    /** Retrieve a single blend by its primary key. */
    public Optional<CoffeeBlend> getBlendById(Long id) {
        return blendRepo.findById(id);
    }

    /**
     * Runs the custom inner-join query defined in the repository.
     * Returns blends paired with their roaster information.
     */
    public List<CoffeeBlend> getBlendsWithRoasterInfo() {
        return blendRepo.fetchBlendsWithRoasterDetails();
    }

    /**
     * Saves a brand-new blend to the database.
     * Catches constraint violations (e.g., duplicate SKU code).
     */
    public CoffeeBlend createBlend(CoffeeBlend blend) {
        try {
            return blendRepo.save(blend);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(
                "Failed to save blend — SKU code '" + blend.getSkuCode()
                + "' might already be taken.", ex);
        }
    }

    /**
     * Modifies an existing blend record.
     * Pulls the current state, overwrites fields, and persists.
     */
    public CoffeeBlend updateBlend(Long id, CoffeeBlend updatedData) {
        CoffeeBlend existing = blendRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Blend with ID " + id + " does not exist."));

        existing.setBlendName(updatedData.getBlendName());
        existing.setSkuCode(updatedData.getSkuCode());
        existing.setFlavorProfile(updatedData.getFlavorProfile());
        existing.setPricePerKg(updatedData.getPricePerKg());
        existing.setBeanType(updatedData.getBeanType());
        existing.setRoaster(updatedData.getRoaster());

        try {
            return blendRepo.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(
                "Update failed — there may be a duplicate SKU code conflict.", ex);
        }
    }
}
