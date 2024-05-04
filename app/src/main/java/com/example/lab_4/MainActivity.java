package com.example.lab_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    String mUrl = "https://webradio.io/api/radio/pi/current-song";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        new RestAsyncTask().start();

        if(isConnected()) {
            Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Only viewing of previously entered entries is available", Toast.LENGTH_SHORT).show();
        }

    }
    private class RestAsyncTask extends Thread {

        MyDatabaseHelper myDB;
        String data = "";
        String artist;
        String title;

        public void run() {

            myDB = new MyDatabaseHelper(MainActivity.this);
            //myDB.deleteAllData();
            //myDB.add("TEST","TEST");

            URL url = null;
            try {
                url = new URL(mUrl);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String line;

            while (!isInterrupted()) {
                if(isConnected()) {
                    data = "";
                    try {
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        while ((line = bufferedReader.readLine()) != null) {
                            data = data + line;
                        }

                        if (!data.isEmpty()) {
                            JSONObject jsonObject = new JSONObject(data);
                            artist = jsonObject.getString("artist");
                            title = jsonObject.getString("title");
                            myDB.addIf(artist, title);
                        }
                        //myDB.add("df","tt");
                        Thread.sleep(20000);

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }finally {
                        httpURLConnection.disconnect();
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }


        }

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetwork()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}