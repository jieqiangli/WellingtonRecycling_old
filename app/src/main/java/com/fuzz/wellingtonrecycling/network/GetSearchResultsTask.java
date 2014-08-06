package com.fuzz.wellingtonrecycling.network;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fuzz.wellingtonrecycling.model.RecyclingSearchResult;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GetSearchResultsTask extends AsyncTask<String, Void, Boolean> {

    private static final String STREET_SEARCH_HOST = "http://wellington.govt.nz/layouts/wcc/GeneralLayout.aspx/GetRubbishCollectionStreets";

    private static final String COLLECTION_DATES_HOST = "http://wellington.govt.nz/services/environment-and-waste/rubbish-and-recycling/collection-days/components/collection-search-results";

    private static DefaultHttpClient httpclient = new DefaultHttpClient();

    private Activity mActivity;

    public GetSearchResultsTask(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if (params == null) {
            return false;
        }

        String searchTerm = params[0];

        HttpResponse response = getSearchResults(searchTerm);
        if (response == null) {
            return false;
        }
        HttpEntity entity = response.getEntity();
        boolean success;

        Intent intent = new Intent("my-event");

        if (response.getStatusLine().getStatusCode() == 200) {
            success = true;
        } else {
            success = false;
        }

        ArrayList<RecyclingSearchResult> results = new ArrayList<RecyclingSearchResult>();

        try {

            if (response == null || response.getStatusLine().getStatusCode() != 200) {
                success = false;
            }

            if (entity != null) {
                InputStream is;

                is = entity.getContent();
                String responseBody = IOUtils.toString(is, "UTF-8");
                Log.d("SearchFragment", responseBody);

                entity.consumeContent();

                JSONObject jObject = new JSONObject(responseBody);
                JSONArray jArray = jObject.getJSONArray("d");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject item = jArray.getJSONObject(i);
                    String key = item.getString("Key");
                    String value = item.getString("Value");
                    RecyclingSearchResult result = new RecyclingSearchResult(key, value);
//                    Pair<String, String> pair = new Pair<String, String>(key, value);
                    results.add(result);
                }
                intent.putExtra("addresses", results);
            } else {
                success = false;
            }

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        // add data
        intent.putExtra("success", success);
        LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent);

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

    }

    private HttpResponse getSearchResults(String searchTerm) {
        try {
            HttpPost httpPost = new HttpPost(STREET_SEARCH_HOST);
            String s = "{ partialStreetName: \"" + searchTerm + "\" }";
            StringEntity entity = new StringEntity(s);

            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");

            httpPost.setEntity(entity);

            HttpResponse response = httpclient.execute(httpPost);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}