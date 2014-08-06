package com.fuzz.wellingtonrecycling;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fuzz.wellingtonrecycling.model.RecyclingSearchResult;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new DisplayResultFragment())
                    .commit();
        }

    }

    public void goToSearch() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                R.anim.card_flip_left_in, R.anim.card_flip_left_out);
        ft.replace(R.id.container, new SearchFragment());
        ft.commit();
    }

    public void goToDisplay(RecyclingSearchResult result) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.card_flip_left_in, R.anim.card_flip_left_out,
                R.anim.card_flip_right_in, R.anim.card_flip_right_out);
        Fragment fragment = new DisplayResultFragment();
        Bundle args = new Bundle();
        args.putParcelable("", result);
        fragment.setArguments(args);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
