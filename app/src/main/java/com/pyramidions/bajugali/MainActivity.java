package com.pyramidions.bajugali;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pyramidions.bajugali.activities.SignIn;
import com.pyramidions.bajugali.fragments.Aboutus;
import com.pyramidions.bajugali.fragments.BajuDeal;
import com.pyramidions.bajugali.fragments.Contactus;
import com.pyramidions.bajugali.fragments.Home;
import com.pyramidions.bajugali.fragments.MyAdvertisement;
import com.pyramidions.bajugali.fragments.MyApartments;
import com.pyramidions.bajugali.fragments.MyOrders;
import com.pyramidions.bajugali.fragments.MyProfile;
import com.pyramidions.bajugali.fragments.Support;
import com.pyramidions.bajugali.helpers.SharedHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedHelper sharedHelper;
    public  static Toolbar toolbar;
    private TextView toolbar_textview,toolbar_textview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         toolbar.setTitle(null);
      //   toolbar.setLogo(R.drawable.logoss);
        toolbar_textview = (TextView) toolbar.findViewById(R.id.toolbar_textview);
        toolbar_textview.setText("BajuGali");
        toolbar_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fragment fragment = new Home();
                    FragmentManager manager = getSupportFragmentManager();
                    @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.flContent, fragment);
                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        sharedHelper = new SharedHelper();
         setSupportActionBar(toolbar);
       /* View view = toolbar.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Cvlicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            Fragment fragment = new Home();
            FragmentManager manager = getSupportFragmentManager();
            @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.flContent, fragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_notification) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            try {
                Fragment fragment = new Home();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_profile) {
            try {
                Fragment fragment = new MyProfile();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_bajudeals) {
            try {
                Fragment fragment = new BajuDeal();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_myapartment) {
            try {
                Fragment fragment = new MyApartments();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_myadvertisement) {
            try {
                Fragment fragment = new MyAdvertisement();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else if (id == R.id.nav_myorders) {
            try {
                Fragment fragment = new MyOrders();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (id == R.id.nav_aboutUs) {
            try {
                Fragment fragment = new Aboutus();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_terms_conditions) {
            try {
                Fragment fragment = new Support();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_privacy_policy) {
            try {
                Fragment fragment = new Contactus();
                FragmentManager manager = getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_Logout) {

            new AlertDialog.Builder(this,R.style.AlertDialogTheme)

                    .setTitle("Do you want to Logout ?")
                    .setMessage("")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String login = sharedHelper.getKey(MainActivity.this, "login_type");
                            if (login.equalsIgnoreCase("Logged")) {
                                Intent logIntent = new Intent(MainActivity.this, SignIn.class);
                                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                logIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                sharedHelper.putKey(MainActivity.this, "login_type", "");
                                startActivity(logIntent);
                                MainActivity.this.finish();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
