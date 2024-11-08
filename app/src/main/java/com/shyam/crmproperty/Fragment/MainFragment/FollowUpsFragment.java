package com.shyam.crmproperty.Fragment.MainFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;
import com.shyam.crmproperty.Fragment.DetailData.DetailDetailFollowupFragment;
import com.shyam.crmproperty.Fragment.ManageData.ManageCustomerFragment;
import com.shyam.crmproperty.Fragment.ProfileFragment;
import com.shyam.crmproperty.R;

import java.util.Objects;


public class FollowUpsFragment extends Fragment {

    public ViewPager2 viewPager2;
    TabLayout tabLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow_ups, container, false);

        intView(view);


        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity());
        viewPager2.setAdapter(pagerAdapter);

        viewPager2.setOffscreenPageLimit(5);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {

            Log.e("ioioioioioioioioioioioi", position + "    " + tab.getPosition());

            switch (position) {
                case 0:
                    tab.setText("All");
                    break;
                case 1:
                    tab.setText("Today");
                    break;
                case 2:
                    tab.setText("Overdue");
                    break;
                case 3:
                    tab.setText("In 7 Day");
                    break;
                case 4:
                    tab.setText("More Then 7 Day");
                    break;
                default:
                    tab.setText("Error");
                    break;
            }
        }).attach();

//        setupSwipeListeners();

        return view;
    }


//    private void setupSwipeListeners() {
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // Disable main ViewPager swipe when inner ViewPager is active
//                ViewPager2 mainViewPager = requireActivity().findViewById(R.id.m_view);
//                if (mainViewPager != null) {
//                    mainViewPager.setUserInputEnabled(state == ViewPager2.SCROLL_STATE_IDLE);
//                }
//            }
//        });
//    }
//

    private void intView(View view) {

        tabLayout = view.findViewById(R.id.fu_tabLayout);
        viewPager2 = view.findViewById(R.id.fu_viewPager);

    }


    public static class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new DetailDetailFollowupFragment(DataConstant.FollowupToday);
                case 2:
                    return new DetailDetailFollowupFragment(DataConstant.FollowupOverdue);
                case 3:
                    return new DetailDetailFollowupFragment(DataConstant.FollowupIn7day);
                case 4:
                    return new DetailDetailFollowupFragment(DataConstant.FollowupMoreThen7day);
                default:
                    return new DetailDetailFollowupFragment(DataConstant.FollowupAll);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

}