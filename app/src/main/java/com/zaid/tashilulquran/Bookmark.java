package com.zaid.tashilulquran;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Bookmark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
//      code here
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDatabaseHandler db=new MyDatabaseHandler(this);
        ArrayList<BookmarkModel> bookmarks=db.getAllBookmark();
        recyclerView.setAdapter(new BookmarkAdapter(bookmarks));

    }
}