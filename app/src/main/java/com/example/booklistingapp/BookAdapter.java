package com.example.booklistingapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by root on 5/13/17.
 */

public class BookAdapter extends ArrayAdapter<BookInfo>{

    private Context mContext;

    public BookAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BookInfo> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHoler mViewHoler;

        if(convertView ==null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_book_row , parent , false);

            mViewHoler = new ViewHoler();
            mViewHoler.mTitleTextView = (TextView) convertView.findViewById(R.id.textView_book_row_title);
            mViewHoler.mAuthorTextView = (TextView) convertView.findViewById(R.id.textView_book_row_author);
            mViewHoler.mPriceTextView = (TextView) convertView.findViewById(R.id.textView_book_row_price);
            mViewHoler.mRatingBar = (RatingBar) convertView.findViewById(R.id.textView_book_row_ratingsBar);
            mViewHoler.mRatingsTextView = (TextView) convertView.findViewById(R.id.textView_book_row_ratings);


            convertView.setTag(mViewHoler);
        }else{
            mViewHoler = (ViewHoler) convertView.getTag();
        }

        BookInfo mBookInfo = getItem(position);

        // sets the book title to TitleTextview
        mViewHoler.mTitleTextView.setText(mBookInfo.getTitle());
        // sets the Author Name to authorTextview
        mViewHoler.mAuthorTextView.setText(mBookInfo.getAuthor());
        // sets the retail price to PriceTextview
        mViewHoler.mPriceTextView.setText(mBookInfo.getRetialPrice());
        // sets the ratings
        float rating = (float) mBookInfo.getRating();
        mViewHoler.mRatingsTextView.setText(""+rating);
        return convertView;
    }

    /**
     * View holder to host the UI
     */
   static class ViewHoler{
       private TextView mTitleTextView;
       private TextView mAuthorTextView;
       private TextView mPriceTextView;
       private TextView mRatingsTextView;
       private RatingBar mRatingBar;

   }
}
