package com.shellintel.shellsim.simulator;

import com.shellintel.shellsim.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TransactionGenerator {

    private final Random random = new Random();

    public List<Transaction> generateTransactions(Long companyId, boolean isShell) {

        List<Transaction> transactions = new ArrayList<>();

        int transactionCount = isShell
                ? random.nextInt(5) + 1        // very few transactions
                : random.nextInt(50) + 20;     // many normal transactions

        for (int i = 0; i < transactionCount; i++) {

            Transaction tx = new Transaction();
            tx.setCompanyId(companyId);

            if (isShell) {
                tx.setAmount(BigDecimal.valueOf(
                        5_000_000 + random.nextInt(50_000_000)
                ));
                tx.setTransactionType("Consulting Fee");
            } else {
                tx.setAmount(BigDecimal.valueOf(
                        10_000 + random.nextInt(500_000)
                ));
                tx.setTransactionType(
                        random.nextBoolean() ? "Payroll" : "Vendor Payment"
                );
            }

            tx.setTransactionDate(
                    LocalDate.now().minusDays(random.nextInt(365))
            );

            transactions.add(tx);
        }

        return transactions;
    }
}

