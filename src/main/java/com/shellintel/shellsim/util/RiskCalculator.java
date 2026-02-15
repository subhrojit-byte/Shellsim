package com.shellintel.shellsim.util;

import com.shellintel.shellsim.model.Address;
import com.shellintel.shellsim.model.Company;
import com.shellintel.shellsim.model.Director;
import com.shellintel.shellsim.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class RiskCalculator {

    public int calculateRisk(Company company,
                             List<Director> companyDirector,
                             List<Transaction> transactions,
                             Address address) {

        int risk = 0;

        BigDecimal revenuePerEmployee =
                company.getDeclaredRevenue()
                        .divide(
                                BigDecimal.valueOf(Math.max(company.getEmployeeCount(), 1)),
                                2,  // scale (decimal places)
                                RoundingMode.HALF_UP
                        );


        if (revenuePerEmployee.compareTo(BigDecimal.valueOf(5_000_000)) > 0)
            risk += 25;

        if (companyDirector.size() <= 1) risk += 20;


        long roundTx = transactions.stream()
                .filter(tx -> tx.getAmount()
                        .remainder(BigDecimal.valueOf(100000))
                        .compareTo(BigDecimal.ZERO) == 0)
                .count();


        if (roundTx > transactions.size() * 0.6)
            risk += 20;

        if (company.getCountry().equalsIgnoreCase("Panama"))
            risk += 20;



//        if (address.getAddressType().equalsIgnoreCase("Virtual"))
            //risk += 15;

        return Math.min(risk, 100);
    }
}
