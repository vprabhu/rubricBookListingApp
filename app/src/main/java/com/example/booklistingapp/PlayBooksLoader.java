package com.example.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by root on 5/13/17.
 */

public class PlayBooksLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String mPlayBooksUrl;
    public PlayBooksLoader(Context context , String urlParams) {
        super(context);
        mPlayBooksUrl = urlParams;
    }

    @Override
    public List<BookInfo> loadInBackground() {
        List<BookInfo>  mBookInfoList = Utils.fetchEarthquakeData(mPlayBooksUrl);

        return mBookInfoList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
