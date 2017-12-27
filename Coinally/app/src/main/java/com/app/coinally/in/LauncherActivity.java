package com.app.coinally.in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;


import com.app.coinally.in.Fragments.AccountsFragment;
import com.app.coinally.in.Fragments.HomeFragment;
import com.app.coinally.in.Fragments.MoreFragment;
import com.app.coinally.in.Fragments.OrderFragment;
import com.app.coinally.in.Utils.BottomNavigationViewHelper;
import com.app.coinally.in.Utils.MenuCustomFont;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences prefs;
    Intent i;
    Context mcontext;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        mcontext = this;

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

       BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        Menu m = bottomNavigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = AccountsFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = OrderFragment.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = MoreFragment.newInstance();
                                break;
//                            case R.id.action_item5:
//                                selectedFragment = ActionFragment.newInstance();
//                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();


        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);


    }

//    public void changefragment() {
//        //Manually displaying the first fragment - one time only
//        transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, TwoTabFragment.newInstance());
//        transaction.commit();
//    }

    // Navigation drawer methods -----------------------------------------------------------
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        //setFragment(0);

//        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//
//        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    //changing font of navigation menu -----------------------------------------------------------------

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.typeface_medium_font));
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new MenuCustomFont("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
//        lockManager.disableAppLock();
//        prefs.edit().putBoolean("isLockShow", false).apply();
    }
}
