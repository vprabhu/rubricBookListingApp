package com.example.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/13/17.
 */

public class Utils {


    private static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }



    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(30000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link BookInfo} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<BookInfo> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<BookInfo> bookQueried = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject mJsonObject = new JSONObject(jsonResponse);
            JSONArray mFeaturesArray = mJsonObject.getJSONArray("items");
            int size = mFeaturesArray.length();
            for (int i = 0; i < size; i++) {
                String title = mFeaturesArray.getJSONObject(i)
                        .getJSONObject("volumeInfo")
                        .getString("title");
                JSONArray mAuthorsJsonArray = mFeaturesArray.getJSONObject(i)
                        .getJSONObject("volumeInfo")
                        .optJSONArray("authors");
                String authors = "";
                if(mAuthorsJsonArray!=null && mAuthorsJsonArray.length()!=0){
                    for (int j = 0; j < mAuthorsJsonArray.length(); j++) {
                        if (j == 0){
                            authors = mAuthorsJsonArray.getString(j);
                        }else {
                            authors = authors +" , " + mAuthorsJsonArray.getString(j);
                        }
                    }
                }

                int pageCount = mFeaturesArray.getJSONObject(i)
                        .getJSONObject("volumeInfo")
                        .optInt("pageCount");
                double ratings = mFeaturesArray.getJSONObject(i)
                        .getJSONObject("volumeInfo")
                        .optDouble("averageRating");

                String isBookForSale = mFeaturesArray.getJSONObject(i)
                        .getJSONObject("saleInfo")
                        .getString("saleability");
                String displayPrice;
                /* FOR_SALE, FREE, NOT_FOR_SALE, or FOR_PREORDER.*/
                if(isBookForSale.equalsIgnoreCase("NOT_FOR_SALE")
                        || isBookForSale.equalsIgnoreCase("FOR_PREORDER")
                        || isBookForSale.equalsIgnoreCase("FREE")){
                    displayPrice = isBookForSale;
                }else {
                    double retailPrice = mFeaturesArray.getJSONObject(i)
                            .getJSONObject("saleInfo")
                            .getJSONObject("retailPrice")
                            .getDouble("amount");
                    String currencyCode = mFeaturesArray.getJSONObject(i)
                            .getJSONObject("saleInfo")
                            .getJSONObject("retailPrice")
                            .getString("currencyCode");;

                    displayPrice = currencyCode +" "+ retailPrice;
                }

                BookInfo mInfo = new BookInfo(title , authors ,pageCount , ratings , displayPrice , "");
                bookQueried.add(mInfo);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return bookQueried;
    }

    /**
     * Query the GooglePlayBooks API and return an {@link BookInfo} object to represent a single earthquake.
     */
    public static List<BookInfo> fetchEarthquakeData(String requestUrl) {

        Log.d(LOG_TAG, "fetchEarthquakeData: ");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<BookInfo> bookInfos = extractEarthquakes(jsonResponse);

        // Return the {@link Event}
        return bookInfos;
    }

    public static boolean isInternetConnected(){


        return false;
    }


}
