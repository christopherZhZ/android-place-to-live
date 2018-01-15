package com.christopherzhz.placetoliveapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.ui.adapters.RoomListAdapter;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;

/**
 * A fragment representing room information list.
 */
public class RoomInfoFragment extends Fragment {

    @BindView(R.id.room_info_list)
    RecyclerView mRoomInfoList;

    private DatabaseReference mDatabaseRef;
    private RoomListAdapter mRoomListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roominfo, container, false);
        return view;
    }


}
