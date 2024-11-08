package com.shyam.crmproperty.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shyam.crmproperty.Fragment.MainFragment.FollowUpsFragment;
import com.shyam.crmproperty.Fragment.MainFragment.HomeFragment;
import com.shyam.crmproperty.Fragment.ManageData.ManageAmenitiesFragment;
import com.shyam.crmproperty.Fragment.ManageData.ManageCustomerFragment;
import com.shyam.crmproperty.Fragment.ManageData.ManageUserFragment;
import com.shyam.crmproperty.Fragment.ProfileFragment;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

public class MainActivity extends AppCompatActivity {

    String[] tabTitles = {"Home", "Customer", "Project", "FollowUp", "Report", "Profile"};
    PrefHandler prefHandler;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle drawerToggle;
    static TabLayout tabLayout;
    public static ViewPager2 viewPager;
    ImageView m_menu_btn;
    FrameLayout m_container;
    LinearLayout draw_nav_home, draw_nav_subscription, draw_nav_follow_up, draw_nav_customer, draw_nav_sms, draw_nav_user_manage, draw_nav_profile, draw_nav_report,
            draw_nav_amenities, draw_nav_project;
    TextView draw_user_email;
    public static TextView m_header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.main_blue));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main_blue));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();

        restoreTabItemSelection();

        draw_user_email.setText(prefHandler.getEMAIL());

        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, 0, 0);

        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        m_menu_btn.setOnClickListener(view -> drawer_layout.open());

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOffscreenPageLimit(6);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(this).inflate(R.layout.nav_bottom_custom_tab, null);
            ImageView imageView = customView.findViewById(R.id.nav_ic_img);
            TextView textView = customView.findViewById(R.id.nav_ic_txt);

            int iconResId = getIconResource(position);
            if (iconResId != 0) {
                imageView.setImageResource(iconResId);
            }

            textView.setText(tabTitles[position]);

            tab.setCustomView(customView);
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        m_header_title.setText("Home");
                        break;
                    case 1:
                        m_header_title.setText("Manage Customers");
                        break;
                    case 2:
                        m_header_title.setText("Projects");
                        break;
                    case 3:
                        m_header_title.setText("Follow Ups...");
                        break;
                    case 4:
                        m_header_title.setText("Report");
                        break;
                    case 5:
                        m_header_title.setText("Profile");
                        break;
                    default:
                        m_header_title.setText("Home");
                        break;
                }
            }
        });

    }


    public void updateHeaderTitle() {

        switch (viewPager.getCurrentItem()) {
            case 0:
                m_header_title.setText("Home");
                break;
            case 1:
                m_header_title.setText("Manage Customers");
                break;
            case 2:
                m_header_title.setText("Projects");
                break;
            case 3:
                m_header_title.setText("Follow Ups...");
                break;
            case 4:
                m_header_title.setText("Report");
                break;
            case 5:
                m_header_title.setText("Profile");
                break;
            default:
                m_header_title.setText("Home");
                break;
        }

    }

    public static void removeTabItemSelection() {
        tabLayout.setSelectedTabIndicator(null);
    }

    public static void loadFragment(FragmentActivity activity, Fragment fragment, boolean fromDrawer) {

        viewPager.setUserInputEnabled(false);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.getBackStackEntryCount() > 0 && !fromDrawer) {
            fragmentManager.popBackStack();
        } else {
//            m_container.setVisibility(View.VISIBLE);
        }

        fragmentTransaction.replace(R.id.m_container, fragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void selectTabItemSelection(int pos) {

        drawer_layout.close();

        viewPager.setCurrentItem(pos, true);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }

    }
    
    private void restoreTabItemSelection() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                tabLayout.setSelectedTabIndicator(R.drawable.nav_bottom_item_selected);

                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                    viewPager.setUserInputEnabled(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    
    private void clickListener() {

        draw_nav_user_manage.setOnClickListener(view -> {
            drawer_layout.close();
            removeTabItemSelection();
            loadFragment(this, new ManageUserFragment(), false);
        });

        draw_nav_amenities.setOnClickListener(v -> {
            drawer_layout.close();
            removeTabItemSelection();
            loadFragment(this, new ManageAmenitiesFragment(), false);
        });

        draw_nav_home.setOnClickListener(v -> selectTabItemSelection(0));

        draw_nav_customer.setOnClickListener(v -> selectTabItemSelection(1));

        draw_nav_project.setOnClickListener(v -> selectTabItemSelection(2));

        draw_nav_follow_up.setOnClickListener(v -> selectTabItemSelection(3));

        draw_nav_report.setOnClickListener(v -> selectTabItemSelection(4));

        draw_nav_profile.setOnClickListener(v -> selectTabItemSelection(5));


    }
    
    private int getIconResource(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_nav_home;
            case 1:
                return R.drawable.ic_nav_user;
            case 2:
                return R.drawable.ic_nav_list;
            case 3:
                return R.drawable.ic_nav_person;
            case 4:
                return R.drawable.ic_nav_report;
            case 5:
                return R.drawable.ic_nav_profile;
            default:
                return 0;
        }
    }

    private void intView() {
        prefHandler = new PrefHandler(this);
        drawer_layout = findViewById(R.id.drawer_layout);
        m_menu_btn = findViewById(R.id.m_menu_btn);
        tabLayout = findViewById(R.id.m_bottom_nav);
        viewPager = findViewById(R.id.m_view);
        m_header_title = findViewById(R.id.m_header_title);
        draw_user_email = findViewById(R.id.draw_user_email);
        draw_nav_home = findViewById(R.id.draw_nav_home);
        draw_nav_subscription = findViewById(R.id.draw_nav_subscription);
        draw_nav_follow_up = findViewById(R.id.draw_nav_follow_up);
        draw_nav_customer = findViewById(R.id.draw_nav_customer);
        draw_nav_sms = findViewById(R.id.draw_nav_sms);
        draw_nav_user_manage = findViewById(R.id.draw_nav_user_manage);
        draw_nav_profile = findViewById(R.id.draw_nav_profile);
        draw_nav_report = findViewById(R.id.draw_nav_report);
        draw_nav_amenities = findViewById(R.id.draw_nav_amenities);
        draw_nav_project = findViewById(R.id.draw_nav_project);
        m_container = findViewById(R.id.m_container);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.m_container);

        if (currentFragment == null) {
            tabLayout.setSelectedTabIndicator(R.drawable.nav_bottom_item_selected);
            viewPager.setUserInputEnabled(true);
            updateHeaderTitle();
            Log.e("jcijdnvystgvjsdsgjsgv", "True : Fragment back stack is null");
        } else {
            Log.e("jcijdnvystgvjsdsgjsgv", "False : Fragment back stack is Not null");
        }
    }

    

    public static class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ManageCustomerFragment();
                case 2:
                    return new ManageCustomerFragment();
                case 3:
                    return new FollowUpsFragment();
                case 4:
                    return new ManageCustomerFragment();
                case 5:
                    return new ProfileFragment();
                default:
                    return new HomeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }

}