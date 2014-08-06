package com.fuzz.wellingtonrecycling;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.fuzz.wellingtonrecycling.model.RecyclingSearchResult;
import com.fuzz.wellingtonrecycling.network.GetSearchResultsTask;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    MainActivity activity;

    AutoCompleteTextView mAutocompleteTextView;

    ArrayAdapter<RecyclingSearchResult> mAdapter;

    public SearchFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setupViews(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("my-event"));
    }

    @Override
    public void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            //HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("addresses");
            ArrayList<RecyclingSearchResult> results = (ArrayList<RecyclingSearchResult>) intent.getSerializableExtra("addresses");
//            mAdapter.clear();
//            mAdapter.addAll(results);
            mAdapter = new SearchResultAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, results);
//            mAdapter.notifyDataSetChanged();
            mAutocompleteTextView.setAdapter(mAdapter);
            mAdapter.getFilter().filter(mAutocompleteTextView.getText().toString());
        }
    };

    private void setupViews(View root) {
        mAdapter = new SearchResultAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<RecyclingSearchResult>());
        mAutocompleteTextView = (AutoCompleteTextView) root.findViewById(R.id.search_text);
        mAutocompleteTextView.setAdapter(mAdapter);
        mAutocompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchTerm = editable.toString();
                if (StringUtils.isNotEmpty(searchTerm) && searchTerm.length() >= 2) {
                    GetSearchResultsTask task = new GetSearchResultsTask(getActivity());
                    task.execute(searchTerm);
                }
            }
        });

        mAutocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecyclingSearchResult result = (RecyclingSearchResult) adapterView.getItemAtPosition(i);
                Log.d("", "Clicked on " + result);
                activity.goToDisplay(result);
            }
        });
    }

}