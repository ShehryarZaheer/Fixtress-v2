package com.example.ehsanullah.loginandregistration.OtherActivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Books.BooksFragment;
import com.example.ehsanullah.loginandregistration.Games.GameFragment;
import com.example.ehsanullah.loginandregistration.Images.ImagesFragment;
import com.example.ehsanullah.loginandregistration.Movies.MoviesFragment;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.Video.VideosFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.io.Serializable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TabsActivity extends DrawerActivity implements Serializable, NavigationView.OnNavigationItemSelectedListener {


    MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        setTitle("");

        //region Initialize ViewPager
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        //endregion

        //region Set ViewPager Adapter
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new VideosFragment();
                    case 1:
                        return new ImagesFragment();
                    case 2:
                        return new BooksFragment();
                    case 3:
                        return new GameFragment();
                    case 4:
                        return new MoviesFragment();
                    default:
                        return new GameFragment();
                }
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Videos";
                    case 1:
                        return "Images";
                    case 2:
                        return "Books";
                    case 3:
                        return "Apps & Games";
                    case 4:
                        return "Movies";
                }
                return "";
            }
        });
        //endregion

        //region Set ViewPagerListener on ViewPager
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });
        //endregion

        //region Setting the ViewPager Clickable
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //endregion

        //region Setting up the DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //endregion


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //region Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_entertainment) {
            // Handle the camera action
        } else if (id == R.id.nav_depression) {

        } else if (id == R.id.nav_HR) {

        } else if (id == R.id.nav_pregnancy) {

        } else if (id == R.id.nav_graph) {

        }

        //endregion

        //region Closing Drawer after selecting the option from the drawer
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
*/
        //endregion

        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new SweetAlertDialog(TabsActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Exit")
                .setContentText("Are you sure you want to exit?")
                .setConfirmText("Exit")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                })
                .setCancelText("Cancel")

                .show();


    }
}
