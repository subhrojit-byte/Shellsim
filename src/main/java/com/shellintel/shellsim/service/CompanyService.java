package com.shellintel.shellsim.service;

import com.shellintel.shellsim.dto.CompanyDTO;
import com.shellintel.shellsim.model.Address;
import com.shellintel.shellsim.model.Company;
import com.shellintel.shellsim.model.Director;
import com.shellintel.shellsim.model.Transaction;
import com.shellintel.shellsim.repository.AddressRepository;
import com.shellintel.shellsim.repository.CompanyRepository;
import com.shellintel.shellsim.repository.DirectorRepository;
import com.shellintel.shellsim.repository.TransactionRepository;
import com.shellintel.shellsim.util.RiskCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RiskCalculator riskCalculator;
    private final DirectorRepository directorRepository;
    private final TransactionRepository transactionRepository;
    private final AddressRepository addressRepository;

    public CompanyService(CompanyRepository companyRepository,
                          RiskCalculator riskCalculator, DirectorRepository directorRepository, TransactionRepository transactionRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.riskCalculator = riskCalculator;
        this.directorRepository = directorRepository;
        this.transactionRepository = transactionRepository;
        this.addressRepository = addressRepository;
    }

    public List<CompanyDTO> getAllCompanies() {

        List<Company> companies = companyRepository.findAll();

        return companies.stream()
                .map(company -> {

                    List<Director> directors =
                            directorRepository.findByCompanyId(company.getId());

                    List<Transaction> transactions =
                            transactionRepository.findByCompanyId(company.getId());

                    Address address =
                            addressRepository.findByCompanyId(company.getId());

                    int risk = riskCalculator.calculateRisk(
                            company,
                            directors,
                            transactions,
                            address
                    );

                    return new CompanyDTO(
                            company.getId(),
                            company.getName(),
                            company.getCountry(),
                            risk
                    );
                })
                .collect(Collectors.toList());
    }


    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

}
