package com.christopherzhz.placetoliveapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherzhz.placetoliveapp.R;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by frankmac on 2018-01-14.
 */

public class AccountFragment extends Fragment {

    private DatabaseReference mDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

}
