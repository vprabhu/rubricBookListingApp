package com.example.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by root on 5/13/17.
 */

public class PlayBooksLoader extends AsyncTaskLoader<List<BookInfo>> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String mPlayBooksUrl;
    public PlayBooksLoader(Context context , String urlParams) {
        super(context);
        Log.d(TAG, "PlayBooksLoader: ");
        mPlayBooksUrl = urlParams;
    }

    @Override
    public List<BookInfo> loadInBackground() {
        Log.d(TAG, "loadInBackground: ");
        // fetch the data from Url ,parse the json response and makes the list
        List<BookInfo>  mBookInfoList = Utils.fetchEarthquakeData(mPlayBooksUrl);
        return mBookInfoList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartLoading: ");
        forceLoad();
    }
}
