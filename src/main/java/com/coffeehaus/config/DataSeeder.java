package com.coffeehaus.config;

import com.coffeehaus.entity.CoffeeBlend;
import com.coffeehaus.entity.Roaster;
import com.coffeehaus.repository.CoffeeBlendRepository;
import com.coffeehaus.repository.RoasterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Populates the H2 database with sample data when the application starts.
 * Creates 10 roasters and 10 blends so the tables are not empty.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final RoasterRepository roasterRepo;
    private final CoffeeBlendRepository blendRepo;

    public DataSeeder(RoasterRepository roasterRepo, CoffeeBlendRepository blendRepo) {
        this.roasterRepo = roasterRepo;
        this.blendRepo = blendRepo;
    }

    @Override
    public void run(String... args) {

        // --- Seed 10 Roasters ---
        Roaster r1  = roasterRepo.save(new Roaster("Blue Bottle Roasters",   "info@bluebottle.co",       "USA",        "Light",     2002));
        Roaster r2  = roasterRepo.save(new Roaster("Lavazza Italia",         "contact@lavazza.it",       "Italy",      "Medium",    1895));
        Roaster r3  = roasterRepo.save(new Roaster("Kaffa Forest Co.",       "hello@kaffaforest.et",     "Ethiopia",   "Specialty", 2010));
        Roaster r4  = roasterRepo.save(new Roaster("Toby's Estate",          "brew@tobysestate.au",      "Australia",  "Medium",    1997));
        Roaster r5  = roasterRepo.save(new Roaster("Bali Spirit Roasters",   "aloha@balispirit.id",      "Indonesia",  "Dark",      2015));
        Roaster r6  = roasterRepo.save(new Roaster("Campos Coffee",          "support@campos.com.au",    "Australia",  "Medium",    2002));
        Roaster r7  = roasterRepo.save(new Roaster("Tandem Coffee",          "hi@tandemcoffee.us",       "USA",        "Light",     2012));
        Roaster r8  = roasterRepo.save(new Roaster("Illy Caffè",             "service@illy.it",          "Italy",      "Dark",      1933));
        Roaster r9  = roasterRepo.save(new Roaster("Koppi Roasters",         "hej@koppi.se",             "Sweden",     "Light",     2007));
        Roaster r10 = roasterRepo.save(new Roaster("Café de Colombia HQ",    "ventas@cafedecolombia.co", "Colombia",   "Specialty", 1960));

        // --- Seed 10 Coffee Blends (each linked to one of the roasters) ---
        blendRepo.save(new CoffeeBlend("Morning Haze",        "SKU-1001", "Citrus & Caramel",        42.50, "Arabica",  r1));
        blendRepo.save(new CoffeeBlend("Napoli Crema",        "SKU-1002", "Chocolate & Hazelnut",    38.00, "Robusta",  r2));
        blendRepo.save(new CoffeeBlend("Wild Yirgacheffe",    "SKU-1003", "Blueberry & Jasmine",     55.00, "Arabica",  r3));
        blendRepo.save(new CoffeeBlend("Sydney Sunrise",      "SKU-1004", "Nutty & Toffee",          44.00, "Arabica",  r4));
        blendRepo.save(new CoffeeBlend("Volcanic Dark",       "SKU-1005", "Smoky & Cocoa",           36.00, "Robusta",  r5));
        blendRepo.save(new CoffeeBlend("Superior Blend",      "SKU-1006", "Honey & Almond",          48.00, "Arabica",  r6));
        blendRepo.save(new CoffeeBlend("Portland Fog",        "SKU-1007", "Stone Fruit & Vanilla",   50.00, "Arabica",  r7));
        blendRepo.save(new CoffeeBlend("Classico Forte",      "SKU-1008", "Dark Chocolate & Spice",  39.50, "Robusta",  r8));
        blendRepo.save(new CoffeeBlend("Nordic Frost",        "SKU-1009", "Berry & Floral",          52.00, "Liberica", r9));
        blendRepo.save(new CoffeeBlend("Andes Reserve",       "SKU-1010", "Brown Sugar & Walnut",    46.00, "Arabica",  r10));

        System.out.println(">>> CoffeeHaus: Sample data loaded — 10 roasters, 10 blends.");
    }
}
