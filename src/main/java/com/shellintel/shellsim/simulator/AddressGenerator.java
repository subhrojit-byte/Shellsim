package com.shellintel.shellsim.simulator;


import com.shellintel.shellsim.model.Address;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AddressGenerator {

    private static final String[] PANAMA_STREETS = {
            "Avenida Balboa 123",
            "Calle 50 Tower 5",
            "Ocean Business Plaza",
            "Marbella Financial Center"
    };

    private static final String[] INDIA_STREETS = {
            "MG Road 45",
            "Sector 62 Tech Park",
            "Salt Lake Block B",
            "Electronic City Phase 1"
    };

    private static final String[] CITIES = {"Mumbai", "Bangalore", "Delhi", "Panama City"};

    private final Random random = new Random();

    public Address generateAddress(boolean isShell) {

        Address address = new Address();

        if (isShell) {
            address.setStreet(PANAMA_STREETS[random.nextInt(PANAMA_STREETS.length)]);
            address.setCity("Panama City");
            address.setCountry("Panama");
            address.setPostalCode("0000" + random.nextInt(999));
        } else {
            address.setStreet(INDIA_STREETS[random.nextInt(INDIA_STREETS.length)]);
            address.setCity(CITIES[random.nextInt(3)]);
            address.setCountry("India");
            address.setPostalCode("5600" + random.nextInt(999));
        }

        return address;
    }
}

