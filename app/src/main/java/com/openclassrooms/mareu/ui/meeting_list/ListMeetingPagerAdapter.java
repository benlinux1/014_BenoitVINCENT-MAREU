package com.openclassrooms.mareu.ui.meeting_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListMeetingPagerAdapter extends FragmentPagerAdapter {

    public ListMeetingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return MeetingFragment.newInstance(); // My Meetings Tab
            default :
                return null;
        }
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 1;
    } // Change number of pages 1 > 2 to includes favorites page
}