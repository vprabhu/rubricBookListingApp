package com.example.booklistingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    private ListView mBookListView;
    private BookAdapter mBookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookListView = (ListView) findViewById(R.id.listView_books);

        // aysnc task to get the queried data from Google Play Books API

        // sets the adapter to ListView
        mBookAdapter = new BookAdapter(
                MainActivity.this ,
                R.layout.layout_book_row ,
                new ArrayList<BookInfo>());

        mBookListView.setAdapter(mBookAdapter);

        getLoaderManager().initLoader(1 , null , MainActivity.this);

    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        return new PlayBooksLoader(MainActivity.this ,
                "https://www.googleapis.com/books/v1/volumes?q=Alien&maxResults=10");
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
        mBookAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        mBookAdapter.clear();
    }
}
