package com.christopherzhz.placetoliveapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.ui.adapters.MainPagerAdapter;
import com.christopherzhz.placetoliveapp.ui.fragments.AccountFragment;
import com.christopherzhz.placetoliveapp.ui.fragments.MapViewFragment;
import com.christopherzhz.placetoliveapp.ui.fragments.RoomInfoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNavView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private MainPagerAdapter mPagerAdapter;
    private MenuItem mPrevMenuItem;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        mBottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_map_view:
                                mViewPager.setCurrentItem(1);
                            case R.id.nav_user:
                                mViewPager.setCurrentItem(2);
                        }
                        return false;
                    }
                }
        );
        
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mBottomNavView.getMenu().getItem(0).setChecked(false);
                }
                mBottomNavView.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mBottomNavView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        disableSwipe();

        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currUser = mAuth.getCurrentUser();
        if (currUser == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(loginIntent);
        } else {
            updateUI(currUser);
        }
    }

    private void updateUI(FirebaseUser currUser) {
        // TODO: update UI
    }

    private void disableSwipe() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

}
