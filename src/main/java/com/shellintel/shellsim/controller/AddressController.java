package com.shellintel.shellsim.controller;

import com.shellintel.shellsim.model.Address;
import com.shellintel.shellsim.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // GET all addresses
    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
