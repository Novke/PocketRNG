package com.novica.pocketrng.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandomOrgApiService implements RNGService{

    private final String url = "https://random.org/integers";

    @Override
    public int generateRandomNumber(int min, int max) throws IOException {
        return generateUsingHttpUrlConnection(min,max);
    }
    private int generateUsingHttpUrlConnection(int min, int max) throws IOException {

        URL url = new URL("https://random.org/integers?num=1&min=" + min + "&max=" + max + "&col=1&base=10&format=plain&rnd=new");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = reader.readLine();
        reader.close();

        return Integer.parseInt(line.split("\t")[0]);

    }


}
