package com.example.mymovieappcs414;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input, content_input, rating_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        content_input = findViewById(R.id.content_input);
        rating_input = findViewById(R.id.rating_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            String title = title_input.getText().toString().trim();
            String author = author_input.getText().toString().trim();
            String content = content_input.getText().toString().trim();
            String ratingStr = rating_input.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || content.isEmpty() || ratingStr.isEmpty()) {
                Toast.makeText(AddActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                return; // Use 'return' to exit the onClick method if validation fails
            }

            // Initialize a variable to store the parsed integer
            int rating = 0;
            // Attempt to parse the integer safely
            try {
                rating = Integer.parseInt(ratingStr);
            } catch (NumberFormatException e) {
                Toast.makeText(AddActivity.this, "Please enter a valid rating", Toast.LENGTH_SHORT).show();
                return; // Exit the onClick method if the number is not valid
            }

            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
            boolean success = myDB.addMovie(title, author, content, rating);

            if (success) {
                Toast.makeText(AddActivity.this, "Movie Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddActivity.this, "Failed to Add Movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
