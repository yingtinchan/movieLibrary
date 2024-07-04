package com.example.mymovieappcs414;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** @noinspection ALL*/
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    Activity activity;
    private ArrayList movie_id, movie_title, movie_author, movie_content, movie_rating;
    private ArrayList<String> movie_id_full;
    private ArrayList<String> movie_title_full;
    private ArrayList<String> movie_author_full;
    private ArrayList<String> movie_content_full;
    private ArrayList<String> movie_rating_full;
    int position;
    CustomAdapter(Activity activity, Context context, ArrayList movie_id, ArrayList movie_title, ArrayList movie_author,
                  ArrayList movie_content, ArrayList movie_rating){
        this.activity = activity;
        this.context = context;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.movie_author = movie_author;
        this.movie_content = movie_content;
        this.movie_rating = movie_rating;


        movie_id_full = new ArrayList<>(movie_id);
        movie_title_full = new ArrayList<>(movie_title);
        movie_author_full = new ArrayList<>(movie_author);
        movie_content_full = new ArrayList<>(movie_content);
        movie_rating_full = new ArrayList<>(movie_rating);
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.movie_id_txt.setText(String.valueOf(movie_id.get(position)));
        holder.movie_title_txt.setText(String.valueOf(movie_title.get(position)));
        holder.movie_author_txt.setText(String.valueOf(movie_author.get(position)));
        holder.movie_content_txt.setText(String.valueOf(movie_content.get(position)));
        holder.movie_rating_txt.setText(String.valueOf(movie_rating.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(movie_id.get(position)));
                intent.putExtra("title",String.valueOf(movie_title.get(position)));
                intent.putExtra("author",String.valueOf(movie_author.get(position)));
                intent.putExtra("content",String.valueOf(movie_content.get(position)));
                intent.putExtra("rating",String.valueOf(movie_rating.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie_id.size();
    }

    // Method to filter data
    public void filterList(String text) {
        text = text.toLowerCase();
        movie_id.clear();
        movie_title.clear();
        movie_author.clear();
        movie_content.clear();
        movie_rating.clear();
        if (text.isEmpty()) {
            movie_id.addAll(movie_id_full);
            movie_title.addAll(movie_title_full);
            movie_author.addAll(movie_author_full);
            movie_content.addAll(movie_content_full);
            movie_rating.addAll(movie_rating_full);
        } else {
            for (int i = 0; i < movie_title_full.size(); i++) {
                if (movie_title_full.get(i).toLowerCase().contains(text) ||
                        movie_author_full.get(i).toLowerCase().contains(text) ||
                        movie_content_full.get(i).toLowerCase().contains(text) ||
                        movie_rating_full.get(i).toLowerCase().contains(text)) {
                    movie_id.add(movie_id_full.get(i));
                    movie_title.add(movie_title_full.get(i));
                    movie_author.add(movie_author_full.get(i));
                    movie_content.add(movie_content_full.get(i));
                    movie_rating.add(movie_rating_full.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

        class MyViewHolder extends RecyclerView.ViewHolder{
        TextView movie_id_txt, movie_title_txt, movie_author_txt, movie_content_txt, movie_rating_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_id_txt = itemView.findViewById(R.id.movie_id_txt);
            movie_title_txt = itemView.findViewById(R.id.movie_title_txt);
            movie_author_txt = itemView.findViewById(R.id.movie_author_txt);
            movie_content_txt = itemView.findViewById(R.id.movie_content_txt);
            movie_rating_txt = itemView.findViewById(R.id.movie_rating_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


}
