package com.pyramidions.bajugali.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Events;
import com.pyramidions.bajugali.activities.SubmitViewAdd;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdvertisement extends Fragment {

    Context context;
    private Button bt_Adv_submit,bt_Adv_rent_sale;
    TextView toolbar_textview;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MyAdvertisement() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_my_advertisement, container, false);
        context = getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
      //  toolbar.setLogo(null);
        // toolbar.setTitle("My ADVERTISEMENT");
      //  toolbar_textview = (TextView) toolbar.findViewById(R.id.toolbar_textview);
      //  toolbar_textview.setText("My ADVERTISEMENT");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        viewPager = (ViewPager) view.findViewById(R.id.viewpagerAdv);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabsAdv);
        tabLayout.setupWithViewPager(viewPager);
        bt_Adv_submit = (Button) view.findViewById(R.id.bt_Adv_submit);
        bt_Adv_rent_sale = (Button) view.findViewById(R.id.bt_Adv_rent_sale);
        bt_Adv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SubmitViewAdd.class));
                Activity activity = (Activity)context;
                activity.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
            }
        });
        bt_Adv_rent_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new SubmitViewAdvFragment(), getActivity().getResources().getString(R.string.submit_view_adv));
        adapter.addFragment(new RentSaleAdvFragment(), getActivity().getResources().getString(R.string.rent_sale));
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
