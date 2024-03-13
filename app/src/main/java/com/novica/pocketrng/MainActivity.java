package com.novica.pocketrng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.novica.pocketrng.services.JavaRNGService;
import com.novica.pocketrng.services.RNGService;
import com.novica.pocketrng.services.RandomOrgApiService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView numJava;
    private TextView numRandomOrg;
    private EditText txtMin;
    private EditText txtMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        numJava = findViewById(R.id.numJava);
        numRandomOrg = findViewById(R.id.numRandomOrg);
        txtMin = findViewById(R.id.txtMin);
        txtMax = findViewById(R.id.txtMax);

        RNGService javaRng = new JavaRNGService();
        RNGService randomOrgRng = new RandomOrgApiService();


        txtMin.setText("1");
        txtMax.setText("100");




        button.setOnClickListener(listener -> {

            int minimum;
            int maximum;

            try {
                 minimum = Integer.parseInt(txtMin.getText().toString());
                 maximum = Integer.parseInt(txtMax.getText().toString());
            } catch (Exception ex){
                Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show();
                return;
            }

            if (minimum>=maximum){
                Toast.makeText(this, "Minimum must be less than maximum", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                int javaNumber = javaRng.generateRandomNumber(minimum, maximum);
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
                        return randomOrgRng.generateRandomNumber(minimum, maximum);
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
                        Toast.makeText(MainActivity.this, "Random.org requires internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        });
    }
}