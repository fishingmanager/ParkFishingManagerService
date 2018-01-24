package com.fishing.namtran.fishingmanagerservice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fishing.namtran.fishingmanagerservice.dbconnection.SessionManagement;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeadersAdapterFactory;

public class ManagerCustomerActivity extends AppCompatActivity { //BaseMenuActivity

    private TableFixHeaders tableFixHeaders;
    private TableFixHeadersAdapterFactory tableFixHeadersAdapterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check login
        SessionManagement session = new SessionManagement(getApplicationContext());
        if(session.isLoggedIn())
        {
            Utils.Redirect(getApplicationContext(), ManagerCustomerActivity.class);
        }

        setContentView(R.layout.activity_manager_customer);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        loadFishingEntriesData();

        if(!isMyServiceRunning(SendMailService.class)) {
            startService(new Intent(this, SendMailService.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.table_original) {
            createTable(TableFixHeadersAdapterFactory.ORIGINAL);
            return true;
        } else if (id == R.id.table_basic) {
            createTable(TableFixHeadersAdapterFactory.BASIC);
            return true;
        } else if (id == R.id.table_original_sortable) {
            createTable(TableFixHeadersAdapterFactory.ORIGINAL_SORTABLE);
            return true;
        }
        */

        if (id == R.id.logout) {
            SessionManagement session = new SessionManagement(getApplicationContext());
            session.logoutUser();
            finish();
            return true;
        } else if (id == R.id.add_customer) {
            Utils.Redirect(getApplicationContext(), AddNewCustomerActivity.class);
            return true;
        } else if (id == R.id.report) {
            Utils.Redirect(getApplicationContext(), ReportActivity.class);
            return true;
        }
        else if (id == R.id.settings) {
            Utils.Redirect(getApplicationContext(), SettingsActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createTable(int type) {
        tableFixHeaders.setAdapter(tableFixHeadersAdapterFactory.getAdapter(type));
    }

    private void loadFishingEntriesData()
    {
        tableFixHeaders = (TableFixHeaders) findViewById(R.id.tablefixheaders);
        tableFixHeadersAdapterFactory = new TableFixHeadersAdapterFactory(this);
        createTable(TableFixHeadersAdapterFactory.ORIGINAL);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadFishingEntriesData();
    }

}
