package com.jordan_remi.app.codescan.FragmentDetailCode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jorda on 06/10/2017.
 */

public class AdapteurPage extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    //On fournit à l'adapter la liste des fragments à afficher
    public AdapteurPage(FragmentManager fm, List fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
