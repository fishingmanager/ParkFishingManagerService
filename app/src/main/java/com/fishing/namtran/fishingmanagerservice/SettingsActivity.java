package com.fishing.namtran.fishingmanagerservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.fishing.namtran.fishingmanagerservice.dbconnection.Settings;
import com.fishing.namtran.fishingmanagerservice.dbconnection.SettingsManager;
import com.fishing.namtran.fishingmanagerservice.dbconnection.UserManager;

/**
 * A login screen that offers login via email/password.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SettingsActionTask mSettingsTask = null;

    // UI references.
    private EditText mPriceFishingView;
    private EditText mPackageFishingView;
    private EditText mPriceBuyFishView;
    private EditText mServerEmailView;
    private EditText mServerPassView;
    private EditText mReceiveEmailView;
    private View mProgressView;
    private View mSubmitFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the login form.
        mPriceFishingView = (EditText) findViewById(R.id.price_fishing);
        mPackageFishingView = (EditText) findViewById(R.id.package_fishing);
        mPriceBuyFishView = (EditText) findViewById(R.id.price_buy_fish);
        mServerEmailView = (EditText) findViewById(R.id.server_email);
        mServerPassView = (EditText) findViewById(R.id.server_pass);
        mReceiveEmailView = (EditText) findViewById(R.id.receive_email);
        mSubmitFormView = findViewById(R.id.settings_form);
        mProgressView = findViewById(R.id.settings_progress);

        //Set value
        SettingsManager setting = new SettingsManager(getApplicationContext());
        Cursor cursor = setting.getSettingEntry("1");
        while(cursor.moveToNext()) {
            mPackageFishingView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.PACKAGE_FISHING)));
            mPriceFishingView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.PRICE_FISHING)));
            mPriceBuyFishView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.PRICE_BUY_FISH)));
            mServerEmailView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.SERVER_EMAIL)));
            mServerPassView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.SERVER_PASSWORD)));
            mReceiveEmailView.setText(cursor.getString(cursor.getColumnIndexOrThrow(Settings.Properties.RECEIVE_EMAIL)));
        }
        cursor.close();

        Button mSettingsButton = (Button) findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmit() {
        if (mSettingsTask != null) {
            return;
        }

        // Reset errors.
        mPackageFishingView.setError(null);
        mPriceFishingView.setError(null);

        // Store values at the time of the login attempt.
        String packageFishing = mPackageFishingView.getText().toString();
        String priceFishing = mPriceFishingView.getText().toString();
        String priceBuyFish = mPriceBuyFishView.getText().toString();

        String serverEmail = mServerEmailView.getText().toString();
        String serverPass = mServerPassView.getText().toString();
        String receiveEmail = mReceiveEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(packageFishing)) {
            mPackageFishingView.setError(getString(R.string.error_field_required));
            focusView = mPackageFishingView;
            cancel = true;
        }
        else
        if (TextUtils.isEmpty(priceFishing)) {
            mPriceFishingView.setError(getString(R.string.error_field_required));
            focusView = mPriceFishingView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mSettingsTask = new SettingsActionTask(packageFishing, priceFishing, priceBuyFish, serverEmail, serverPass, receiveEmail);
            mSettingsTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSubmitFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous action task
     */
    public class SettingsActionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPackageFishing;
        private final String mPriceFishing;
        private final String mPriceBuyFish;
        private final String mServerEmail;
        private final String mServerPass;
        private final String mReceiveEmail;

        SettingsActionTask(String packageFishing, String priceFishing, String priceBuyFish, String serverEmail, String serverPass, String receiveEmail) {
            mPackageFishing = packageFishing;
            mPriceFishing = priceFishing;
            mPriceBuyFish = priceBuyFish;
            mServerEmail = serverEmail;
            mServerPass = serverPass;
            mReceiveEmail = receiveEmail;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSettingsTask = null;
            showProgress(false);
            SettingsManager setting = new SettingsManager(getApplicationContext());
            if (success) {
                if(setting.updateSettings("1", mPackageFishing, mPriceFishing, mPriceBuyFish, mServerEmail, mServerPass, mReceiveEmail))
                {
                    finish();
                }
                else
                {
                    Utils.Alert(SettingsActivity.this, getString(R.string.action_error));
                }
            } else {
                Utils.Alert(SettingsActivity.this, getString(R.string.action_error));
            }
        }

        @Override
        protected void onCancelled() {
            mSettingsTask = null;
            showProgress(false);
        }
    }
}

