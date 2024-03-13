package com.novica.pocketrng.services;

import java.util.Random;

public class JavaRNGService implements RNGService {
    @Override
    public int generateRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
