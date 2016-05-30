package in.yousee.jeevandaan;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import in.yousee.jeevandaan.model.CustomException;
import in.yousee.jeevandaan.model.SessionData;
import in.yousee.jeevandaan.model.SummaryModel;

public class MainActivity extends YouseeCustomActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationsFragment.OnFragmentInteractionListener, SummaryFragment.OnFragmentInteractionListener, FactsFragment.OnFragmentInteractionListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getHeaderView(0);
        instantiateViews(navigationView);

        Fragment summaryFragment = new SummaryFragment();
        replaceFragmentOnMainContent(summaryFragment, "Summary");

    }

    TextView donorNameView;
    TextView bloodGroupView;
    private void instantiateViews(NavigationView navigationView)
    {
        View view = navigationView.getHeaderView(0);
        donorNameView = (TextView) view.findViewById(R.id.donor_name);
        bloodGroupView = (TextView) view.findViewById(R.id.blood_group_value);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        String title = "Summary";
        int id = item.getItemId();

        if (id == R.id.nav_locations) {
            fragment = new LocationsFragment();
            title = "Locations";
        } else if (id == R.id.nav_summary) {
            fragment = new SummaryFragment();
            title = "Summary";
        } else if (id == R.id.nav_facts) {
            fragment = new FactsFragment();
            title = "Did You Know?";
        }
        else if (id == R.id.nav_logout) {
            showConfirmationDialog();
            //logout();
            //backToLogin();
        }
        if(fragment != null)
        {
            replaceFragmentOnMainContent(fragment,title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout()
    {
        SessionHandler sessionHandler = new SessionHandler(this);
        try {
            sessionHandler.logout(this);
            finish();

        } catch (CustomException e)
        {

        }

    }
    public void backToLogin()
    {

        Log.i("tag", "in Show menu activity");
        Intent intent = new Intent();
        intent.setClass(this, in.yousee.jeevandaan.LoginActivity.class);
        startActivity(intent);
    }

    private void showConfirmationDialog()
    {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to Logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                        logout();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void replaceFragmentOnMainContent(Fragment fragment, String title)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view,fragment).commit();
        setTitle(title);
    }
    @Override
    public void onResponseRecieved(Object response, int requestCode) {
        SummaryModel summaryModel = (SummaryModel) response;
        donorNameView.setText(summaryModel.donorName);
        bloodGroupView.setText(summaryModel.bloodGroup);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private GoogleMap mMap;



}
