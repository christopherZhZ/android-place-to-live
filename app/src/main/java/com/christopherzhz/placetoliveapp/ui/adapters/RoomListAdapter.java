package com.christopherzhz.placetoliveapp.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.common.models.RoomInfoRow;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RoomListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseRef;
    private ArrayList<DataSnapshot> mDataSnapshots;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mDataSnapshots.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public RoomListAdapter(Activity activity, DatabaseReference ref) {
        mActivity = activity;
        mDatabaseRef = ref.child("");
        mDataSnapshots = new ArrayList<>();
    }

    static class ViewHolder {
        TextView title;
        TextView detail;
        TextView price;
        ImageView roomPreview;
    }

    @Override
    public int getCount() {


        return 0;
    }

    @Override
    public RoomInfoRow getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_roominfo_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.room_row_title);
            viewHolder.detail = (TextView) convertView.findViewById(R.id.room_row_detail);
            viewHolder.price = (TextView) convertView.findViewById(R.id.room_row_price);
            viewHolder.roomPreview = (ImageView) convertView.findViewById(R.id.room_row_preview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final RoomInfoRow roomInfo = getItem(position);

        viewHolder.title.setText(roomInfo.getTitle());
        viewHolder.price.setText(roomInfo.getPrice());
        viewHolder.detail.setText(roomInfo.getDetail());
        // TODO: handle image upload



        return null;
    }

}
