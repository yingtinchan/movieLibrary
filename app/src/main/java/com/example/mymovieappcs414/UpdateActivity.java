package com.example.mymovieappcs414;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, author_input, content_input, rating_input;
    Button update_button, delete_button;
    String id, title, author, content, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        content_input = findViewById(R.id.content_input2);
        rating_input = findViewById(R.id.rating_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();  //First We call this

        ActionBar ab = getSupportActionBar();    //Set action Bar Title
        if(ab != null){
            ab.setTitle(title);
        }

        update_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            title = title_input.getText().toString().trim();
            author = author_input.getText().toString().trim();
            content = content_input.getText().toString().trim();
            rating = rating_input.getText().toString().trim();
            myDB.updateData(id, title, author, content, rating);
            finish();      //Saved & Go back to previous Activity
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("author")
                && getIntent().hasExtra("content")
                && getIntent().hasExtra("rating")){
            //Getting the Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            content = getIntent().getStringExtra("content");
            rating = getIntent().getStringExtra("rating");

            //Setting Intent Data
            title_input.setText(title);
            author_input.setText(author);
            content_input.setText(content);
            rating_input.setText(rating);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + title + " ?");
        builder.setMessage("Are you sure to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
                    public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        builder.create().show();
    }
}