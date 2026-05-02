package com.coffeehaus.entity;

import jakarta.persistence.*;

/**
 * Represents a specific coffee blend produced by a roaster.
 * Each blend belongs to exactly one roaster.
 */
@Entity
@Table(name = "coffee_blends")
public class CoffeeBlend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blendId;

    @Column(nullable = false, length = 120)
    private String blendName;

    @Column(unique = true, nullable = false, length = 20)
    private String skuCode;  // Stock Keeping Unit — must be unique

    @Column(length = 60)
    private String flavorProfile;  // e.g., "Nutty & Chocolate", "Fruity & Floral"

    private Double pricePerKg;

    @Column(length = 30)
    private String beanType;  // Arabica, Robusta, Liberica, etc.

    // Many blends belong to one roaster
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roaster_id", nullable = false)
    private Roaster roaster;

    public CoffeeBlend() {
    }

    public CoffeeBlend(String blendName, String skuCode, String flavorProfile,
                       Double pricePerKg, String beanType, Roaster roaster) {
        this.blendName = blendName;
        this.skuCode = skuCode;
        this.flavorProfile = flavorProfile;
        this.pricePerKg = pricePerKg;
        this.beanType = beanType;
        this.roaster = roaster;
    }

    // --- Getters and Setters ---

    public Long getBlendId() {
        return blendId;
    }

    public void setBlendId(Long blendId) {
        this.blendId = blendId;
    }

    public String getBlendName() {
        return blendName;
    }

    public void setBlendName(String blendName) {
        this.blendName = blendName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getFlavorProfile() {
        return flavorProfile;
    }

    public void setFlavorProfile(String flavorProfile) {
        this.flavorProfile = flavorProfile;
    }

    public Double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(Double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }

    public Roaster getRoaster() {
        return roaster;
    }

    public void setRoaster(Roaster roaster) {
        this.roaster = roaster;
    }

    @Override
    public String toString() {
        return "CoffeeBlend{" +
                "blendId=" + blendId +
                ", blendName='" + blendName + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", beanType='" + beanType + '\'' +
                '}';
    }
}
