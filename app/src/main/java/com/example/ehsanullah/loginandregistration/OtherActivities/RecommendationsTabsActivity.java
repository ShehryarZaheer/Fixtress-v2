package com.example.ehsanullah.loginandregistration.OtherActivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ehsanullah.loginandregistration.Graph.GraphFragment;
import com.example.ehsanullah.loginandregistration.R;

import java.io.Serializable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecommendationsTabsActivity extends DrawerActivity implements Serializable, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Fragment tabsFragment,graphFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer2);
        setTitle("");



        tabsFragment = new TabsFragment();
        loadFragment(tabsFragment);
        //region Setting up the DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //endregion


    }

    void loadFragment(Fragment fragment) {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //region Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_entertainment) {
            // Handle the camera action
            drawer.closeDrawers();
            loadFragment(new TabsFragment());
        } else if (id == R.id.nav_depression) {


        } else if (id == R.id.nav_HR) {

        } else if (id == R.id.nav_pregnancy) {

        } else if (id == R.id.nav_graph) {
            drawer.closeDrawers();
            loadFragment(new GraphFragment());
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
        new SweetAlertDialog(RecommendationsTabsActivity.this, SweetAlertDialog.WARNING_TYPE)
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
