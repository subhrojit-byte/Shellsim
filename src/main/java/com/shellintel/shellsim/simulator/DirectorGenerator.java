package com.shellintel.shellsim.simulator;

import com.shellintel.shellsim.model.Director;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DirectorGenerator {

    private final Random random = new Random();
    private final List<Director> directorPool = new ArrayList<>();

    String[] FIRST_NAMES = {
            "Raj", "Amit", "Carlos", "Luis", "Maria", "Sanjay", "Vikram",
            "Arjun", "Rohan", "Kunal", "Aditya", "Nikhil", "Rahul",
            "Priya", "Anjali", "Sneha", "Pooja", "Neha", "Kavya",
            "Ishaan", "Dev", "Aryan", "Manish", "Varun", "Tarun",
            "Simran", "Aisha", "Fatima", "Zara", "Meera", "Diya",
            "John", "Michael", "David", "Daniel", "James", "Joseph",
            "Sophia", "Olivia", "Emma", "Ava", "Isabella", "Mia",
            "Noah", "Liam", "Ethan", "Lucas", "Mateo", "Diego"
    };

    private static final String[] LAST_NAMES = {
            "Sharma", "Gupta", "Fernandez", "Rodriguez", "Mehta", "Khan",
            "Patel", "Singh", "Verma", "Iyer", "Nair", "Reddy",
            "Kapoor", "Malhotra", "Chatterjee", "Banerjee", "Mukherjee",
            "Das", "Sinha", "Yadav", "Joshi", "Desai", "Pillai",
            "Bose", "Kulkarni", "Agrawal", "Bhat", "Chauhan", "Tiwari",
            "Gonzalez", "Martinez", "Lopez", "Garcia", "Hernandez",
            "Torres", "Flores", "Rivera", "Sanchez", "Ramirez",
            "Carter", "Mitchell", "Turner", "Parker", "Evans",
            "Edwards", "Collins", "Stewart", "Morris", "Rogers"
    };


    public Director generateDirector(boolean isShell) {

        // Shell companies reuse directors heavily
        if (isShell && !directorPool.isEmpty() && random.nextBoolean()) {
            return directorPool.get(random.nextInt(directorPool.size()));
        }

        Director director = new Director();

        String name = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " "
                + LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        director.setName(name);
        director.setNationality(isShell ? "Panama" : "India");

        directorPool.add(director);

        return director;
    }
}


