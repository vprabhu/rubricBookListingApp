package com.example.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView mBookListView;
    private BookAdapter mBookAdapter;
    private TextView mAbsenceTextView;
    private ProgressBar mProgressBar;

    private String mQueryUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        UI Type casting
        mBookListView = (ListView) findViewById(R.id.listView_books);
        mAbsenceTextView = (TextView) findViewById(R.id.textView_absence);
        SearchView mBookSearchView = (SearchView) findViewById(R.id.searchView_play_books);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // displays the TextView if the listview is empty
        mBookListView.setEmptyView(mAbsenceTextView);
        mAbsenceTextView.setText(getResources().getString(R.string.info_empty_list));

        // if the network is connected , the loader is initialized
        // else the "No internet is connected" is displayed to User
        if(isInternetConnected(MainActivity.this)){
            // sets the adapter to ListView
            mBookAdapter = new BookAdapter(
                    MainActivity.this ,
                    R.layout.layout_book_row ,
                    new ArrayList<BookInfo>());

            mBookListView.setAdapter(mBookAdapter);

            getLoaderManager().initLoader(1 , null , MainActivity.this);
        }else {
            mBookSearchView.setVisibility(View.GONE);
            mBookListView.setVisibility(View.GONE);
            mAbsenceTextView.setVisibility(View.VISIBLE);
            mAbsenceTextView.setText(getResources().getString(R.string.info_no_internet));
        }

        mBookSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // change the visibility of UI components to show progressbar
                mProgressBar.setVisibility(View.VISIBLE);
                mBookListView.setVisibility(View.GONE);
                mAbsenceTextView.setVisibility(View.GONE);
                // constructing the url with user entered data
                mQueryUrl ="https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=10";
                // restart the loader to refetch the new queried data from API
                getLoaderManager().restartLoader(1 , null , MainActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        return new PlayBooksLoader(MainActivity.this ,
                mQueryUrl   );
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
        Log.d(TAG, "onLoadFinished: ");
        // change the visibility of UI components to show queried data
        mProgressBar.setVisibility(View.GONE);
        mBookListView.setVisibility(View.VISIBLE);
        mAbsenceTextView.setVisibility(View.VISIBLE);
        // clears list so that to insert new queried data
        mBookAdapter.clear();
        // checks if the new data is not empty
        if(!data.isEmpty()){
            // add the new data into the adapter
            mBookAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        Log.d(TAG, "onLoaderReset: ");
        // clears the data
        mBookAdapter.clear();
    }

    /**
     * checks if the mobile is connected to internet
     * @param context to obtaind the system service
     * @return boolean
     * returns true if network is connected
     * returns false if network is not connected
     */
    private boolean isInternetConnected(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
    }
}
