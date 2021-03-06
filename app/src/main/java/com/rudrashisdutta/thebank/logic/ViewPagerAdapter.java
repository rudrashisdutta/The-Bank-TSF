package com.rudrashisdutta.thebank.logic;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rudrashisdutta.thebank.ui.fragments.CustomersFragment;
import com.rudrashisdutta.thebank.ui.fragments.TransactionsFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;
    private TextView supportActionBar;
    private static FragmentActivity fragmentActivity;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        ViewPagerAdapter.fragmentActivity = fragmentActivity;
        fragments = new ArrayList<Fragment>(){
            {
                add(new CustomersFragment());
                add(new TransactionsFragment());
            }
        };
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }
}
