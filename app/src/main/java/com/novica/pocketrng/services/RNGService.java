package com.novica.pocketrng.services;

import java.io.IOException;

public interface RNGService {

    public int generateRandomNumber(int min, int max) throws IOException;

}
