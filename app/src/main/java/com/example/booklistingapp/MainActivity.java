package com.example.booklistingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mBookListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookListView = (ListView) findViewById(R.id.listView_books);

        /*List<BookInfo> mBookInfoList = new ArrayList<>();
        mBookInfoList.add(new BookInfo("Title 1" , "Author 1" , 330 , 4.5 , "USD 9.99" , "Link"));
        mBookInfoList.add(new BookInfo("Title 2" , "Author 2" , 330 , 4.2 , "USD 9.99" , "Link"));
        mBookInfoList.add(new BookInfo("Title 3" , "Author 3" , 330 , 4.45 , "USD 9.99" , "Link"));
        mBookInfoList.add(new BookInfo("Title 4" , "Author 4" , 330 , 4.6 , "USD 9.99" , "Link"));
        mBookInfoList.add(new BookInfo("Title 5" , "Author 5" , 330 , 4.1 , "USD 9.99" , "Link"));
        mBookInfoList.add(new BookInfo("Title 6" , "Author 6" , 330 , 3.7, "USD 9.99" , "Link"));
        // placeholder data list*/

        // aysnc task to get the queried data from Google Play Books API

        new BookAysncTask().execute("https://www.googleapis.com/books/v1/volumes?q=comics&maxResults=10");

    }


    private class BookAysncTask extends AsyncTask<String , Void , List<BookInfo>>{


        @Override
        protected List<BookInfo> doInBackground(String... params) {

           List<BookInfo>  mBookInfoList = Utils.fetchEarthquakeData(params[0]);

            return mBookInfoList;
        }

        @Override
        protected void onPostExecute(List<BookInfo> bookInfos) {
            super.onPostExecute(bookInfos);

            // sets the adapter to ListView
            BookAdapter mBookAdapter = new BookAdapter(
                    MainActivity.this ,
                    R.layout.layout_book_row ,
                    bookInfos);

            mBookListView.setAdapter(mBookAdapter);

        }
    }
}
