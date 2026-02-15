package com.shellintel.shellsim.injector;

import com.shellintel.shellsim.model.Company;
import com.shellintel.shellsim.model.Director;
import com.shellintel.shellsim.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
@Component
public class ShellBehaviorInjector {
    public void injectDirectorReuse(Company company) {

    }

    public void injectCircularTransactions(Company company) {
        // A → B → C → A
    }

    public void injectEarlyTransactionSpike(Company company) {
        // high activity soon after incorporation
    }

    private final Random random = new Random();

    public void injectShellPattern(Company company, List<Transaction> transactions , List<Director> directors ){
        int choice = random.nextInt(3); // 0, 1, or 2
        switch (choice) {
            case 0:
                injectDirectorReuse(company);
                break;
            case 1:
                injectCircularTransactions(company);
                break;
            case 2:
                injectEarlyTransactionSpike(company);
                break;
        }

    }
}
