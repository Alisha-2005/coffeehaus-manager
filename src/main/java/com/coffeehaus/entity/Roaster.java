package com.coffeehaus.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a coffee roasting company or artisan roaster.
 * Each roaster can produce multiple coffee blends.
 */
@Entity
@Table(name = "roasters")
public class Roaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roasterId;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(unique = true, nullable = false, length = 150)
    private String contactEmail;

    @Column(length = 80)
    private String originCountry;

    @Column(length = 30)
    private String roastStyle;  // e.g., Light, Medium, Dark, Specialty

    private Integer foundedYear;

    // One roaster produces many blends
    @OneToMany(mappedBy = "roaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeBlend> blends = new ArrayList<>();

    // --- No-argument constructor required by JPA ---
    public Roaster() {
    }

    // --- Parameterized constructor for convenience ---
    public Roaster(String companyName, String contactEmail, String originCountry,
                   String roastStyle, Integer foundedYear) {
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.originCountry = originCountry;
        this.roastStyle = roastStyle;
        this.foundedYear = foundedYear;
    }

    // --- Accessor and mutator methods ---

    public Long getRoasterId() {
        return roasterId;
    }

    public void setRoasterId(Long roasterId) {
        this.roasterId = roasterId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getRoastStyle() {
        return roastStyle;
    }

    public void setRoastStyle(String roastStyle) {
        this.roastStyle = roastStyle;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public List<CoffeeBlend> getBlends() {
        return blends;
    }

    public void setBlends(List<CoffeeBlend> blends) {
        this.blends = blends;
    }

    @Override
    public String toString() {
        return "Roaster{" +
                "roasterId=" + roasterId +
                ", companyName='" + companyName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", originCountry='" + originCountry + '\'' +
                '}';
    }
}
