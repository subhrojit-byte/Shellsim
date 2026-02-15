package com.shellintel.shellsim.simulator;

import com.shellintel.shellsim.model.Company;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class CompanyGenerator {

    private static final Random random = new Random();

    private static final List<String> LEGIT_COUNTRIES = List.of(
            "India", "USA", "Germany", "UK", "Canada"
    );

    private static final List<String> OFFSHORE_COUNTRIES = List.of(
            "Panama", "British Virgin Islands", "Cayman Islands", "Seychelles"
    );

    private static final List<String> INDUSTRIES = List.of(
            "Technology", "Manufacturing", "Consulting",
            "Logistics", "Energy", "Holdings"
    );

    private static final List<String> COMPANY_PREFIX = List.of(
            "Global", "Prime", "Vertex", "BlueWave",
            "SilverLine", "Apex", "Summit", "Zenith"
    );

    private static final List<String> COMPANY_SUFFIX = List.of(
            "Solutions", "Industries", "Holdings",
            "Corporation", "Enterprises", "Group"
    );

    public static Company generate(boolean isShell) {

        Company company = new Company();

        company.setName(generateCompanyName());
        company.setIndustry(randomFrom(INDUSTRIES));
        company.setIsShellTruth(isShell);

        if (isShell) {
            generateShellCompany(company);
        } else {
            generateLegitCompany(company);
        }

        company.setRiskScore(calculateRisk(company));

        return company;
    }

    private static void generateShellCompany(Company company) {

        company.setCountry(randomFrom(OFFSHORE_COUNTRIES));

        // Recently incorporated
        company.setIncorporationDate(
                LocalDate.now().minusMonths(random.nextInt(18))
        );

        // Very few employees
        company.setEmployeeCount(random.nextInt(3));

        // High revenue despite low staff
        company.setDeclaredRevenue(
                BigDecimal.valueOf(10_000_000 + random.nextInt(50_000_000))
        );
    }

    private static void generateLegitCompany(Company company) {

        company.setCountry(randomFrom(LEGIT_COUNTRIES));

        // Older company
        company.setIncorporationDate(
                LocalDate.now().minusYears(3 + random.nextInt(15))
        );

        int employees = 20 + random.nextInt(500);
        company.setEmployeeCount(employees);

        // Revenue proportional to employees
        double revenue = employees * (200_000 + random.nextInt(300_000));

        company.setDeclaredRevenue(
                BigDecimal.valueOf(revenue)
        );
    }

    private static String generateCompanyName() {
        return randomFrom(COMPANY_PREFIX) + " "
                + randomFrom(COMPANY_SUFFIX) + " Pvt Ltd";
    }

    private static <T> T randomFrom(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private static int calculateRisk(Company company) {

        int score = 0;

        if (OFFSHORE_COUNTRIES.contains(company.getCountry())) {
            score += 30;
        }

        if (company.getEmployeeCount() <= 2) {
            score += 25;
        }

        if (company.getDeclaredRevenue().doubleValue() > 10_000_000
                && company.getEmployeeCount() <= 2) {
            score += 30;
        }

        if (company.getIncorporationDate().isAfter(LocalDate.now().minusYears(2))) {
            score += 15;
        }

        return score;
    }
}


