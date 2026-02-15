package com.shellintel.shellsim.util;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class RandomUtil {
    private final Random random = new Random();

    public int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public boolean randomBoolean(double probability) {
        return random.nextDouble() < probability;
    }

    public String randomFromArray(String[] array) {
        return array[random.nextInt(array.length)];
    }
}

