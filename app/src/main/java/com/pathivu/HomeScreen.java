package com.pathivu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.pathivu.Sections.AddFriends.UI.AddFriendsFragment;
import com.pathivu.Sections.Dialpad.UI.DialpadFragment;
import com.pathivu.Sections.Feeds.UI.FeedsFragment;
import com.pathivu.Sections.Feeds.UI.LoadFeedDetails;
import com.pathivu.Sections.Friends.UI.FriendsFragment;
import com.pathivu.Sections.Media.UI.MediaFragment;
import com.pathivu.Sections.Messages.UI.ComposeMessageActivity;
import com.pathivu.Sections.Messages.UI.MessagesFragment;
import com.pathivu.Sections.Settings.UI.SettingsFragment;
import com.pathivu.Utils.Logger;


public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private int _id = -1;

    private Fragment mFragment = null;

    private FloatingActionButton mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.menu_title_1));
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Logger.ShowToast(HomeScreen.this, "Hi");

//                if (mFragment != null && mFragment == new MessagesFragment()) {
//
//                }

                if (_id == R.id.messages) {
                    startActivity(new Intent(HomeScreen.this, ComposeMessageActivity.class));
                }
            }
        });

        SetInitialFragment(new FeedsFragment(), 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mMenuInflater = this.getMenuInflater();
        MenuItem mSearchItem = menu.findItem(R.id.action_search);
        SearchManager mSearchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = null;

        if (_id == R.id.friends) {

            mActionButton.setVisibility(View.GONE);

            mMenuInflater.inflate(R.menu.menu_friends, menu);

            if (mSearchItem != null) {
                mSearchView = (SearchView) mSearchItem.getActionView();
                mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(this.getComponentName()));
            }

        } else if (_id == R.id.messages) {

            mActionButton.setVisibility(View.VISIBLE);

            mMenuInflater.inflate(R.menu.menu_friends, menu);

            if (mSearchItem != null) {
                mSearchView = (SearchView) mSearchItem.getActionView();
                mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(this.getComponentName()));
            }
        } else if (_id == R.id.media) {
            mActionButton.setVisibility(View.GONE);
        } else if (_id == R.id.dialer) {
            mActionButton.setVisibility(View.GONE);
        } else if (_id == R.id.settings) {
            mActionButton.setVisibility(View.GONE);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        _id = item.getItemId();

        mFragment = null;

        if (_id == R.id.feeds) {

            mToolbar.setTitle(getString(R.string.menu_title_1));
            mFragment = new FeedsFragment();

        } else if (_id == R.id.friends) {
            mToolbar.setTitle(getString(R.string.menu_title_2));
            mFragment = new FriendsFragment();

        } /*else if (_id == R.id.addfriends) {
            mToolbar.setTitle(getString(R.string.menu_title_3));
            mFragment = new AddFriendsFragment();

        }*/ else if (_id == R.id.messages) {
            mToolbar.setTitle(getString(R.string.menu_title_4));
            mFragment = new MessagesFragment();

        } else if (_id == R.id.dialer) {
            mToolbar.setTitle(getString(R.string.menu_title_5));
            mFragment = new DialpadFragment();

        } else if (_id == R.id.media) {
            mToolbar.setTitle(getString(R.string.menu_title_6));
            mFragment = new MediaFragment();

        } else if (_id == R.id.settings) {
            mToolbar.setTitle(getString(R.string.menu_title_7));
            mFragment = new SettingsFragment();

        } else if (_id == R.id.logout) {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            invalidateOptionsMenu();

            return true;
        }

        if (mFragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.homecontents, mFragment).commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            invalidateOptionsMenu();

            return true;
        }

        return false;
    }

    private void SetInitialFragment(Fragment mView, int position) {

        mNavigationView.getMenu().getItem(position).setChecked(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homecontents, mView).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
