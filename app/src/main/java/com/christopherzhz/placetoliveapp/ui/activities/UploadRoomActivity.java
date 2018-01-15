package com.christopherzhz.placetoliveapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.common.C;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadRoomActivity extends AppCompatActivity {

    @BindView(R.id.upload_back_button)
    ImageButton mBackButton;
    @BindView(R.id.upload_post_button)
    ImageButton mPostButton;
    @BindView(R.id.upload_title)
    EditText mTitleField;
    @BindView(R.id.upload_detail)
    EditText mDetailField;
    @BindView(R.id.upload_image_button)
    ImageButton mPostPictureButton;
    @BindView(R.id.upload_price_field)
    EditText mPriceField;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room);
        ButterKnife.bind(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passInfoAndBackToHome(false, null, null, null);
            }
        });
    }

    private void passInfoAndBackToHome(boolean success, String title, String detail, String price) {
        Intent roomInfoResultIntent = new Intent();
        roomInfoResultIntent.putExtra("title", title);
        roomInfoResultIntent.putExtra("detail", detail);
        roomInfoResultIntent.putExtra("price", price);

        if (success) {
            setResult(Activity.RESULT_OK, roomInfoResultIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, roomInfoResultIntent);
        }
        Log.d(C.DEBUG_TAG, "passInfoAndBackToHome: finished/quited upload page");
        finish();
    }

    private String getDisplayName() {
        String savedName = getSharedPreferences(C.ROOM_PREFS, MODE_PRIVATE).getString(C.ONSCREEN_USERNAME_KEY, null);
        return (savedName == null)? "Anonymous" : savedName;
    }

}
