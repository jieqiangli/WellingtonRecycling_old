package com.fuzz.wellingtonrecycling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.fuzz.wellingtonrecycling.model.RecyclingSearchResult;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/07/2014.
 */
public class SearchResultAdapter extends ArrayAdapter<RecyclingSearchResult> {
    private ArrayList<RecyclingSearchResult> objects;
    private LocationFilter mFilter;

    public SearchResultAdapter(Context context, int textViewResourceId, ArrayList<RecyclingSearchResult> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new LocationFilter();
        }
        return mFilter;
    }

    @Override
    public RecyclingSearchResult getItem(int i) {
        if (objects == null) {
            return null;
        }
        return objects.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(android.R.layout.simple_dropdown_item_1line, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        RecyclingSearchResult i = objects.get(position);

        if (i != null) {
            TextView tv = (TextView) v.findViewById(android.R.id.text1);
            tv.setText(i.getStreet());
        }

        // the view must be returned to our activity
        return v;

    }

    private class LocationFilter extends Filter {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            if (constraint == null || StringUtils.isEmpty(constraint.toString()) || getCount() == 0) {
                return null;
            }
            List<String> resultsSuggestions = new ArrayList<String>();
            for (int i = 0; i < getCount(); i++) {
                if(StringUtils.startsWithIgnoreCase(getItem(i).getStreet(), constraint)){
                    resultsSuggestions.add(getItem(i).getStreet());
                }
            }
            FilterResults results = new FilterResults();
            results.values = resultsSuggestions;
            results.count = resultsSuggestions.size();
//            notifyDataSetChanged();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            if (results != null && results.count > 0) {
                //objects = (ArrayList<RecyclingSearchResult>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
}
