package com.shellintel.shellsim.service;

import com.shellintel.shellsim.injector.ShellBehaviorInjector;
import com.shellintel.shellsim.model.Address;
import com.shellintel.shellsim.model.Company;
import com.shellintel.shellsim.model.Director;
import com.shellintel.shellsim.model.Transaction;
import com.shellintel.shellsim.repository.AddressRepository;
import com.shellintel.shellsim.repository.CompanyRepository;
import com.shellintel.shellsim.repository.DirectorRepository;
import com.shellintel.shellsim.repository.TransactionRepository;
import com.shellintel.shellsim.simulator.AddressGenerator;
import com.shellintel.shellsim.simulator.CompanyGenerator;
import com.shellintel.shellsim.simulator.DirectorGenerator;
import com.shellintel.shellsim.simulator.TransactionGenerator;
import com.shellintel.shellsim.util.RiskCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

    private final CompanyGenerator companyGenerator;
    private final CompanyRepository companyRepo;
    private final AddressGenerator addressGenerator;
    private final AddressRepository addressRepository;
    private final DirectorGenerator directorGenerator;
    private final DirectorRepository directorRepository;
    private final TransactionGenerator transactionGenerator;
    private final TransactionRepository transactionRepository;
    private final ShellBehaviorInjector injector;
    private final RiskCalculator riskCalculator;

    @Value("${simulation.total}")
    private int totalCompanies;

    @Value("${simulation.shell-ratio}")
    private double shellRatio;

    public SimulationService(
            CompanyGenerator companyGenerator,
            CompanyRepository companyRepo,
            AddressGenerator addressGenerator,
            AddressRepository addressRepository,
            DirectorGenerator directorGenerator,
            DirectorRepository directorRepository,
            TransactionGenerator transactionGenerator,
            TransactionRepository transactionRepository,
            ShellBehaviorInjector injector,
            RiskCalculator riskCalculator
    ) {
        this.companyGenerator = companyGenerator;
        this.companyRepo = companyRepo;
        this.addressGenerator = addressGenerator;
        this.addressRepository = addressRepository;
        this.directorGenerator = directorGenerator;
        this.directorRepository = directorRepository;
        this.transactionGenerator = transactionGenerator;
        this.transactionRepository = transactionRepository;
        this.injector = injector;
        this.riskCalculator = riskCalculator;
    }

    public void runSimulation() {



        for (int i = 0; i < totalCompanies; i++) {

            boolean isShell = Math.random() < shellRatio;

            // 1️⃣ Generate company
            Company company = companyGenerator.generate(isShell);
            Long companyId = companyRepo.save(company);
            company.setId(companyId);

            // 2️⃣ Generate and link address
            Address address = addressGenerator.generateAddress(isShell);
            Long addressId = addressRepository.save(address);
            addressRepository.linkCompanyAddress(companyId, addressId);

            // 3️⃣ Generate directors
            List<Director> directors = generateDirectors(company);

            // 4️⃣ Generate transactions
            List<Transaction> transactions =
                    transactionGenerator.generateTransactions(companyId, isShell);
            transactionRepository.saveAll(transactions);

            // 5️⃣ Inject shell-specific behavior (after transactions exist)
            if (isShell) {
                injector.injectShellPattern(company, transactions, directors);
            }

            // 6️⃣ Calculate risk score
            int riskScore = riskCalculator.calculateRisk(company,directors,transactions,address);

            // 7️⃣ Update company with risk
            companyRepo.updateRiskScore(companyId, riskScore);
        }
    }

    private List<Director> generateDirectors(Company company) {

        List<Director> companyDirectors = new ArrayList<>();

        int directorCount = company.getIsShellTruth()
                ? 1 + (int)(Math.random() * 2)   // 1–2
                : 2 + (int)(Math.random() * 3);  // 2–4

        for (int i = 0; i < directorCount; i++) {

            Director director = directorGenerator.generateDirector(company.getIsShellTruth());

            // Save only if new
            if (director.getId() == null) {
                Long directorId = directorRepository.save(director);
                director.setId(directorId);
            }

            // Link to company
            directorRepository.linkCompanyDirector(company.getId(), director.getId());

            companyDirectors.add(director);
        }

        return companyDirectors;
    }
}
