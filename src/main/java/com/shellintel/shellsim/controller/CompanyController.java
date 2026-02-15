package com.shellintel.shellsim.controller;

import com.shellintel.shellsim.model.Company;
import com.shellintel.shellsim.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyRepository companyRepo;

    public CompanyController(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @GetMapping
    public List<Company> getCompanies() {
        return companyRepo.findAll();
    }


}

