package com.novica.pocketrng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.novica.pocketrng.services.JavaRNGService;
import com.novica.pocketrng.services.RNGService;
import com.novica.pocketrng.services.RandomOrgApiService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView numJava;
    private TextView numRandomOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        numJava = findViewById(R.id.numJava);
        numRandomOrg = findViewById(R.id.numRandomOrg);

        RNGService javaRng = new JavaRNGService();
        RNGService randomOrgRng = new RandomOrgApiService();

        button.setOnClickListener(listener -> {
            try {
                int javaNumber = javaRng.generateRandomNumber(1, 100);
                numJava.setText(String.valueOf(javaNumber));
            } catch (IOException e) {
                e.printStackTrace();
                numJava.setText("/");
            }

            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    try {
                        numRandomOrg.setText("...");
                        return randomOrgRng.generateRandomNumber(1, 100);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Integer result) {
                    if (result != null) {
                        numRandomOrg.setText(String.valueOf(result));
                    } else {
                        numRandomOrg.setText("/");
                    }
                }
            }.execute();
        });
    }
}