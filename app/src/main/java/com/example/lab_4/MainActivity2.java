package com.example.lab_4;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;

    MyDatabaseHelper myDB;

    ArrayList<String> _id, Performer, Name, Date;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);


        myDB = new MyDatabaseHelper(MainActivity2.this);

        _id = new ArrayList<>();
        Performer = new ArrayList<>();
        Name = new ArrayList<>();
        Date = new ArrayList<>();

        displayData();

        customAdapter = new CustomAdapter(MainActivity2.this, _id, Performer, Name, Date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));




    }

    void displayData(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                _id.add(cursor.getString(0));
                Performer.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                Date.add(cursor.getString(3));
            }
        }
    }

}