package com.example.mymovieappcs414;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> movie_id, movie_title, movie_author, movie_content, movie_rating;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Movie library");

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        movie_id = new ArrayList<>();
        movie_title = new ArrayList<>();
        movie_author = new ArrayList<>();
        movie_content = new ArrayList<>();
        movie_rating = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,this, movie_id, movie_title, movie_author,
            movie_content, movie_rating);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        // Initialize the SearchView and set its listener
        searchView = findViewById(R.id.searchView); // Make sure you have a SearchView in your layout with this ID
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: If you want to filter as the user types
                customAdapter.filterList(newText);
                return false;
            }
        });
    }

    public void navigateToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override   //Update immediately function
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                movie_id.add(cursor.getString(0));
                movie_title.add(cursor.getString(1));
                movie_author.add(cursor.getString(2));
                movie_content.add(cursor.getString(3));
                movie_rating.add(cursor.getString(4));
            }
        }
    }

}