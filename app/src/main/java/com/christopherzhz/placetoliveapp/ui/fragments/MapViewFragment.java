package com.christopherzhz.placetoliveapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherzhz.placetoliveapp.R;
import com.google.firebase.database.DatabaseReference;

/**
 * A fragment representing room information in map view.
 */
public class MapViewFragment extends Fragment {

    private DatabaseReference mDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

}
