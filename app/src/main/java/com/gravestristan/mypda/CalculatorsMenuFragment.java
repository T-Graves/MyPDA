package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the calculators menu fragment. It contains the basic calculator and tip calculator.
 * Created by Tristan on 3/1/2016.
 */
public class CalculatorsMenuFragment extends Fragment implements AppStatics {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;


    /**
     * The onCreate method for the calculator menu fragment. it sets hasOptionsMenu to true.
     * @param savedInstanceState The bundle being passed in.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * The onCreateView method for the calculator menu fragment. It sets up the view pager that is
     * used to swap between calculators.
     * @param inflater The layout inflater being passed in.
     * @param container The view group being passed in.
     * @param savedInstanceState The bundle being passed in.
     * @return The view that is created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculators_menu, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new BasicCalculatorFragment(), "Basic");
        adapter.addFragment(new TipCalculatorFragment(), "Tip");
        mViewPager.setAdapter(adapter);

        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    /**
     * The view pager adapter that is used in the fragment.
     */
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        /**
         * The constructor for the view pager adapter.
         * @param manager The fragment manager being passed in.
         */
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         * The getItem method used to get what ever fragment is at the position that is passed in.
         * @param position The position being passed in.
         * @return The fragment found at the position that is given.
         */
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        /**
         * The getCount method gets the size of the fragment list.
         * @return The amount of fragments in the list.
         */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * The addFragment method adds fragments and their titles to the lists.
         * @param fragment The fragment to be added.
         * @param title The fragments title to be added at the same time.
         */
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /**
         * The getPageTitle method gets the fragments title at the position that is given.
         * @param position The position of the requested title.
         * @return The title found at the position given.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /**
     * The onOptionsItemSelected method. Handles what happens when an option item is pressed.
     * @param item The menu items.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
