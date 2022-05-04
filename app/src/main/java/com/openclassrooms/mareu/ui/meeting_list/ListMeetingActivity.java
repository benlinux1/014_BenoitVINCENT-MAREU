package com.openclassrooms.mareu.ui.meeting_list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.mareu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMeetingActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    ListMeetingPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListMeetingPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }
}
